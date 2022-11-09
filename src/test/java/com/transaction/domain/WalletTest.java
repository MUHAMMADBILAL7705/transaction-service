package com.transaction.domain;

import com.transaction.enums.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class WalletTest {


    @Test
    void givenNullBalanceWhileSubtractingShouldNotUpdateBalance() {
        Wallet wallet = Wallet.builder().balance(new BigDecimal(100)).build();
        wallet.subtractBalance(null);
        Assertions.assertEquals(new BigDecimal(100), wallet.getBalance());
    }

    @Test
    void givenNullBalanceWhileAddingShouldNotUpdateBalance() {
        Wallet wallet = Wallet.builder().balance(new BigDecimal(100)).build();
        wallet.addBalance(null);
        Assertions.assertEquals(new BigDecimal(100), wallet.getBalance());
    }

    @Test
    void given50BalanceWhileSubtractingShouldUpdateBalance() {
        Wallet wallet = Wallet.builder().balance(new BigDecimal(100)).build();
        wallet.subtractBalance(new BigDecimal(50));
        Assertions.assertEquals(new BigDecimal(50), wallet.getBalance());
    }

    @Test
    void given50BalanceWhileAddingShouldUpdateBalance() {
        Wallet wallet = Wallet.builder().balance(new BigDecimal(100)).build();
        wallet.addBalance(new BigDecimal(50));
        Assertions.assertEquals(new BigDecimal(150), wallet.getBalance());
    }

    @Test
    void givenNullBalanceWhileGettingShouldReturnZero() {
        Wallet wallet = Wallet.builder().build();
        Assertions.assertEquals(new BigDecimal(0), wallet.getBalance());
    }

    @Test
    void givenTransactionWhenValidationShouldReturnValid() {
        Transaction entity = Transaction.builder()
                .amount(new BigDecimal(100))
                .description("for testing")
                .purpose("USB purchase")
                .creditAccount("1234")
                .type(TransactionType.DEBIT)
                .reference("123")
                .transactionDate(LocalDateTime.now())
                .id(1L)
                .build();
        try {
            Wallet wallet = Wallet.builder().balance(new BigDecimal(200)).build();
            wallet.validateBalance(entity);
        } catch (Exception ex) {
            Assertions.fail();
        }
    }

    @Test
    void givenTransactionWhenValidationShouldReturnInValid() {
        Transaction entity = Transaction.builder()
                .amount(new BigDecimal(200))
                .description("added for test")
                .purpose("bought a new phone")
                .creditAccount("1234")
                .type(TransactionType.DEBIT)
                .reference("123")
                .transactionDate(LocalDateTime.now())
                .id(1L)
                .build();
        try {
            Wallet wallet = Wallet.builder().balance(new BigDecimal(0)).build();
            wallet.validateBalance(entity);
            Assertions.fail();
        } catch (Exception ex) {
        }
    }

}