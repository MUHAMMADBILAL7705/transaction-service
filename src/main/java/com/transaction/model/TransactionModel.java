package com.transaction.model;

import com.transaction.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {

    private BigDecimal amount;
    private String purpose;
    private String description;
    private String creditAccount;
    private TransactionType transactionType;
}
