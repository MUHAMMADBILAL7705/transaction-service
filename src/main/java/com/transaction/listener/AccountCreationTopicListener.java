package com.transaction.listener;

import com.transaction.domain.Account;
import com.transaction.domain.NotificationType;
import com.transaction.domain.Wallet;
import com.transaction.exception.DuplicateAccountException;
import com.transaction.exception.DuplicateWalletException;
import com.transaction.service.AccountService;
import com.transaction.service.NotificationService;
import com.transaction.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountCreationTopicListener {

    private final AccountService accountService;
    private final WalletService walletService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "ACCOUNT_CREATION", groupId = "ACCOUNT_CREATION")
    @Transactional
    public void listenToEmailTopics(Account account) throws DuplicateAccountException, DuplicateWalletException {
        Account savedAccount = accountService.save(account);
        Wallet wallet = Wallet.builder()
                .account(savedAccount)
                .build();
        walletService.save(wallet);
        notificationService.sendEmail(NotificationType.CREATE_ACCOUNT, savedAccount.getEmail());
    }
}
