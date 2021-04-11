package com.example.controller;

import com.example.entity.AccountEntity;
import com.example.entity.AccountTransactionEntity;
import com.example.model.EndpointOperationResponsePayload;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import java.util.List;

@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @GetMapping("/account/all")
    public List<AccountEntity> findAll()
    {
        return accountService.findAll();
    }

    @PostMapping("/account/create")
    public ResponseEntity<Object> createAccount(@RequestBody AccountEntity account)
    {
        return accountService.addAccount(account);
    }

}
