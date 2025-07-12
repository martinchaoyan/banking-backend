package com.CloudBanking.Banking.repository;

import com.CloudBanking.Banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

