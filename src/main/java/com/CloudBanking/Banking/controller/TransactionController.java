package com.CloudBanking.Banking.controller;

import com.CloudBanking.Banking.model.Transaction;
import com.CloudBanking.Banking.repository.AccountRepository;
import com.CloudBanking.Banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;  

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository txRepo;

    @Autowired
    private AccountRepository accountRepo;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return txRepo.findAll();
    }

    @PostMapping
    public Transaction createTransaction(@RequestParam Long accountId, @RequestBody Transaction tx) {
        return accountRepo.findById(accountId).map(account -> {
            tx.setAccount(account);
            return txRepo.save(tx);
        }).orElseThrow(() -> new RuntimeException("The account does not exist"));
    }
}
