package com.transaction.service;

import com.transaction.domain.NotificationType;
import com.transaction.domain.Transaction;
import com.transaction.domain.Wallet;
import com.transaction.exception.AccountNotFoundException;
import com.transaction.exception.DuplicateWalletException;
import com.transaction.exception.InSufficientBalanceException;
import com.transaction.repository.TransactionRepository;
import com.transaction.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    @Override
    public BigDecimal calculateBalance( String email) throws AccountNotFoundException {
        Wallet wallet = walletRepository.findByAccount_email(email).orElseThrow(AccountNotFoundException::new);
        return wallet.getBalance();
    }

    @Override
    public void save(Wallet wallet) throws DuplicateWalletException {
        if (walletRepository.findByAccount_email(wallet.getAccount().getEmail()).isPresent())
            throw new DuplicateWalletException();
        walletRepository.save(wallet);
    }


    @Override
    @Transactional
    public Transaction performTransaction(String email, Transaction transaction) throws InSufficientBalanceException, AccountNotFoundException {
        Wallet wallet = walletRepository.findByAccount_email(email).orElseThrow(AccountNotFoundException::new);
        transaction.setWallet(wallet);
        wallet.validateBalance(transaction);
        transaction = transactionRepository.save(transaction);
        updateBalanceAndNotify(transaction, wallet);
        walletRepository.save(wallet);
        return transaction;
    }

    private void updateBalanceAndNotify(Transaction transaction, Wallet wallet) {
        switch (transaction.getType()) {
            case DEBIT:
                wallet.subtractBalance(transaction.getAmount());
                //TODO: Can update this method as per need not passing any transaction data for now
                notificationService.sendEmail(NotificationType.DEBIT_TRANSACTION, wallet.getAccount().getEmail());
                break;
            case CREDIT:
                wallet.addBalance(transaction.getAmount());
                //TODO: Can update this method as per need not passing any transaction data for now
                notificationService.sendEmail(NotificationType.CREDIT_TRANSACTION, wallet.getAccount().getEmail());
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
