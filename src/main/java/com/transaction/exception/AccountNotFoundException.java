package com.transaction.exception;

public class AccountNotFoundException extends Exception {
    private final static String MESSAGE = "Account not found";

    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
