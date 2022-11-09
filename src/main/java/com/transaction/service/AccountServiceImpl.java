package com.transaction.service;

import com.transaction.domain.Account;
import com.transaction.exception.DuplicateAccountException;
import com.transaction.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) throws DuplicateAccountException {
        if (accountRepository.findByEmailIgnoreCase(account.getEmail()) != null)
            throw new DuplicateAccountException();
        return accountRepository.save(account);
    }
}
