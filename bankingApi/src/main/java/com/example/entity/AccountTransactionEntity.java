package com.example.entity;

import com.sun.istack.NotNull;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "account_transaction")
@Table(name = "account_transaction")
public class AccountTransactionEntity {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(nullable = false)
    private int sendingAccountId;

    @NotNull
    @Column(nullable = false)
    private int receivingAccountId;

    @NotNull
    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendingAccountId() {
        return sendingAccountId;
    }

    public void setSendingAccountId(int sendingAccountId) {
        this.sendingAccountId = sendingAccountId;
    }

    public int getReceivingAccountId() {
        return receivingAccountId;
    }


    public void setReceivingAccountId(int receivingAccountId) {
        this.receivingAccountId = receivingAccountId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

}
