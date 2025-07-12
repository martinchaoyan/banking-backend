package com.CloudBanking.Banking;

import com.CloudBanking.Banking.model.*;
import com.CloudBanking.Banking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepo, AccountRepository accountRepo, TransactionRepository txRepo) {
        return args -> {
            Optional<User> existingUser = userRepo.findByEmail("alice@example.com");

            if (existingUser.isEmpty()) {
                // Create users for test
                User user = new User();
                user.setUsername("Alice");
                user.setEmail("alice@example.com");
                user.setPassword("password123");
                userRepo.save(user);

                // Create accounts for test
                Account account = new Account();
                account.setAccountNumber("ACC10001");
                account.setBalance(new BigDecimal("5000.00"));
                account.setAccountType("SAVINGS");
                account.setUser(user);
                accountRepo.save(account);

                // Create transaction for test
                Transaction tx = new Transaction();
                tx.setType("DEPOSIT");
                tx.setAmount(new BigDecimal("1000.00"));
                tx.setDescription("Initial deposit");
                tx.setAccount(account);
                txRepo.save(tx);

                System.out.println("Data initialize: User Alice, Account ACC10001, Transaction 1000 dollars");
            } else {
                System.out.println("Already exists user");
            }
        };
    }
}
