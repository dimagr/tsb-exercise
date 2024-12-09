package com.tsb.exercise.transactions.entities;


import java.math.BigDecimal;
import java.util.Date;

public record TransactionEntity(Long id, Date date, BigDecimal amount, Long fromAccount, Long toAccountId) {

    public TransactionEntity(Long id, TransactionEntity transactionEntity){
        this(id, new Date(), transactionEntity.amount, transactionEntity.fromAccount, transactionEntity.toAccountId);
    }
}
