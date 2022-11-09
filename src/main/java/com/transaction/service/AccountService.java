package com.transaction.service;


import com.transaction.domain.Account;
import com.transaction.exception.DuplicateAccountException;

public interface AccountService {
    Account save(Account account) throws DuplicateAccountException;
}
