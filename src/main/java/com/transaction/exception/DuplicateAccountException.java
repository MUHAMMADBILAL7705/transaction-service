package com.transaction.exception;

public class DuplicateAccountException extends Exception {
    private final static String MESSAGE = "Account already exits";

    public DuplicateAccountException() {
        super(MESSAGE);
    }
}
