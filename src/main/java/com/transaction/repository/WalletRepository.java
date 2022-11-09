package com.transaction.repository;

import com.transaction.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByAccount_Id(Long accountId);
    Optional<Wallet> findByAccount_email(String email);
}
