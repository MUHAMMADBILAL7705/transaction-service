/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transaction.domain;

import com.transaction.enums.TransactionType;
import com.transaction.exception.InSufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account account;
    private BigDecimal balance;
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    public void addBalance(BigDecimal balance) {
        if (balance != null)
            this.balance = getBalance().add(balance);
    }

    public void subtractBalance(BigDecimal balance) {
        if (balance != null)
            this.balance = getBalance().subtract(balance);
    }

    public void validateBalance(Transaction transaction) throws InSufficientBalanceException {
        if (transaction.getType().equals(TransactionType.DEBIT) && transaction.getAmount().compareTo(getBalance()) > 0) {
            throw new InSufficientBalanceException();
        }
    }

    public BigDecimal getBalance(){
        return this.balance == null ? BigDecimal.ZERO : this.balance;
    }
}
