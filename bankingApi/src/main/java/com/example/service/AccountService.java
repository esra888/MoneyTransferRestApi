package com.example.service;

import com.example.entity.AccountEntity;
import com.example.entity.AccountTransactionEntity;
import com.example.entity.TransactionStatus;
import com.example.model.*;
import com.example.reporsitory.AccountRepository;
import com.example.reporsitory.AccountTransactionrepository;
//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private AccountTransactionrepository accountTransactionrepository;

    public AccountService(AccountRepository accountRepository, AccountTransactionrepository accountTransactionrepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionrepository = accountTransactionrepository;
    }


    public List<AccountEntity> findAll() {
       return accountRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Object> addAccount(AccountEntity model) {

        AccountEntity newAccount = new AccountEntity();
        newAccount.setCurrencyCode(model.getCurrencyCode());
        newAccount.setBalance(model.getBalance());

        AccountEntity savedAccount = accountRepository.save(newAccount);

        if (accountRepository.findById(savedAccount.getId()).isPresent()) {
            return ResponseEntity.ok("Account Created Successfully");
        }
        else return ResponseEntity.unprocessableEntity().body("Failed Creating Account as Specified");
    }
}
