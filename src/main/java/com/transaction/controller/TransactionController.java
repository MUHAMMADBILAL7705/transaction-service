package com.transaction.controller;

import com.transaction.exception.AccountNotFoundException;
import com.transaction.exception.InSufficientBalanceException;
import com.transaction.handler.TransactionHandler;
import com.transaction.model.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionHandler transactionHandler;

    @PostMapping("/{email}")
    public TransactionModel performTransaction(@PathVariable String email, @RequestBody TransactionModel transactionModel) throws InSufficientBalanceException, AccountNotFoundException {
        return transactionHandler.performTransaction(email, transactionModel);
    }
}
