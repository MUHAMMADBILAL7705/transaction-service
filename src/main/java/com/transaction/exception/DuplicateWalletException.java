package com.transaction.exception;

public class DuplicateWalletException extends Exception {
    private final static String MESSAGE = "Wallet already exits";

    public DuplicateWalletException() {
        super(MESSAGE);
    }
}
