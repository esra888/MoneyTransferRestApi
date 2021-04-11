package com.example.controller;

import com.example.entity.AccountTransactionEntity;
import com.example.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountTransactionController {

    @Autowired
    AccountTransactionService accountTransactionService;

    @GetMapping("/accounting/all")
    public List<AccountTransactionEntity> findAll() {
        return accountTransactionService.findAll();
    }

    @PostMapping("/accounting")
    public ResponseEntity<Object> createAccounting(@RequestBody AccountTransactionEntity accountTransactionEntity)
    {
        return accountTransactionService.createMoneyTransaction(accountTransactionEntity);
    }
}
