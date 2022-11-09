package com.transaction.exception;

public class InSufficientBalanceException extends Exception {
    private final static String MESSAGE = "Insufficient balance";

    public InSufficientBalanceException() {
        super(MESSAGE);
    }
}
