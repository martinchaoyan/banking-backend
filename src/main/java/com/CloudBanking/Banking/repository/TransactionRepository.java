package com.CloudBanking.Banking.repository;

import com.CloudBanking.Banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
