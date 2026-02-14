package com.bankappv2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankappv2.entity.BankUser;

public interface BankUserRepo extends JpaRepository<BankUser, Long> {
    Optional<BankUser> findByUsername(String username);
}
