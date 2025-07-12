package com.CloudBanking.Banking.controller;

import com.CloudBanking.Banking.model.Account;
import com.CloudBanking.Banking.repository.AccountRepository;
import com.CloudBanking.Banking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    // Get accounts by Id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Account createAccount(@RequestParam Long userId, @RequestBody Account account) {
        return userRepo.findById(userId).map(user -> {
            account.setUser(user);
            return accountRepo.save(account);
        }).orElseThrow(() -> new RuntimeException("The user does not exist"));
    }
}
