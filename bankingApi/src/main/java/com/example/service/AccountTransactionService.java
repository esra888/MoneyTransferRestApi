package com.example.service;

import com.example.entity.AccountEntity;
import com.example.entity.AccountTransactionEntity;
import com.example.reporsitory.AccountRepository;
import com.example.reporsitory.AccountTransactionrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountTransactionService {

    @Autowired
    private AccountRepository accountRepository;
    private AccountTransactionrepository accountTransactionrepository;

    public AccountTransactionService(AccountTransactionrepository accountTransactionrepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionrepository = accountTransactionrepository;
    }

    public List<AccountTransactionEntity> findAll() {
        return accountTransactionrepository.findAll();
    }

    @Transactional
    public ResponseEntity<Object> createMoneyTransaction(AccountTransactionEntity accountTransactionEntity){

        if (doesAccountExistById(accountTransactionEntity.getSendingAccountId()) && doesAccountExistById(accountTransactionEntity.getReceivingAccountId()))
        {
            AccountEntity sendingAccountEntity = accountRepository.findById(accountTransactionEntity.getSendingAccountId()).get();
            AccountEntity receiverAccountEntity = accountRepository.findById(accountTransactionEntity.getReceivingAccountId()).get();

            if (canUserInitiateMoneyTransfer(accountTransactionEntity.getSendingAccountId(), accountTransactionEntity.getTransactionAmount())) {

                if(isAccountsHaveSameCurrencyCode(sendingAccountEntity.getCurrencyCode(), receiverAccountEntity.getCurrencyCode()))
                {
                    try {
                        updateUserAccountBalancesInDatastore(accountTransactionEntity.getSendingAccountId(), accountTransactionEntity.getReceivingAccountId(), accountTransactionEntity.getTransactionAmount());
                        addAccounting(accountTransactionEntity);
                    } catch (Exception ex) {
                        return ResponseEntity.unprocessableEntity().body("Failed Creating Accounting as Specified");
                    }
                    return ResponseEntity.ok("Account Created Successfully");
                }
                return ResponseEntity.status(403).body("Two account have different Currency Code.");
            }
            String reasonForFailure = "Not Enough Balance to initiate transaction";
            return ResponseEntity.status(403).body(reasonForFailure);//we dont want to expose financial information with "not enough balance"
        }
        return  ResponseEntity.unprocessableEntity().body("Account does not exist.");
    }


    public boolean doesAccountExistById(Integer id) {
        if(accountRepository.findById(id).isEmpty()){
            return false;
        }
        return true;
    }

    private boolean canUserInitiateMoneyTransfer(int SendingAccountNumber, BigDecimal TransactionAmount) {

        AccountEntity senderAccount = accountRepository.findById(SendingAccountNumber).get();

        //if comparisonResult =-1,then it's less than value ,0=equal,1=greater
        int comparisonResult =
                senderAccount.getBalance().compareTo(TransactionAmount);
        if (comparisonResult >= 0)
            return true;
        return false;
    }

    public boolean isAccountsHaveSameCurrencyCode(String ReceiverCurrencyCode, String SendingCurrencyCode)
    {
        if(ReceiverCurrencyCode.equals(SendingCurrencyCode)){
            return true;
        }
        return false;
    }

    @Transactional
    public ResponseEntity<Object> addAccounting(AccountTransactionEntity accountTransactionEntity) {
        AccountTransactionEntity newAccounting = new AccountTransactionEntity();
        newAccounting.setReceivingAccountId(accountTransactionEntity.getSendingAccountId());
        newAccounting.setSendingAccountId(accountTransactionEntity.getSendingAccountId());
        newAccounting.setTransactionAmount(accountTransactionEntity.getTransactionAmount());

        AccountTransactionEntity savedAccounting = accountTransactionrepository.save(newAccounting);

        if (accountTransactionrepository.findById(savedAccounting.getId()).isPresent()) {
            return ResponseEntity.ok("Accounting Created Successfully");
        }
        else return ResponseEntity.unprocessableEntity().body("Failed Creating Accounting as Specified");
    }


    @Transactional
    void updateUserAccountBalancesInDatastore(int SendingAccountNumber, int ReceiverAccountnumber, BigDecimal TransactionAmount) {
        AccountEntity senderAccount = accountRepository.findById(SendingAccountNumber).get();
        AccountEntity receiverAccount = accountRepository.findById(ReceiverAccountnumber).get();

        AccountEntity updatedSenderAccountBalance = debitAccountEntity(senderAccount, TransactionAmount);
        AccountEntity updatedRecieverAccountBalance = creditAccountEntity(receiverAccount, TransactionAmount);


        AccountEntity savedSenderAccount = accountRepository.save(updatedSenderAccountBalance);
        AccountEntity savedReceiverAccount = accountRepository.save(updatedRecieverAccountBalance);

    }

    private AccountEntity creditAccountEntity(AccountEntity accountEntity, BigDecimal amountToCredit) {

        BigDecimal currentBalanceBeforeAddition = accountEntity.getBalance();

        BigDecimal currentBalanceAfterAddition = currentBalanceBeforeAddition.add(amountToCredit);

        accountEntity.setBalance(currentBalanceAfterAddition);

        return accountEntity;
    }

    private AccountEntity debitAccountEntity(AccountEntity accountEntity, BigDecimal amountToDebit) {

        BigDecimal currentBalanceBeforeDebit = accountEntity.getBalance();

        BigDecimal currentBalanceAfterDebit = currentBalanceBeforeDebit.subtract(amountToDebit);

        accountEntity.setBalance(currentBalanceAfterDebit);

        return accountEntity;
    }
}


