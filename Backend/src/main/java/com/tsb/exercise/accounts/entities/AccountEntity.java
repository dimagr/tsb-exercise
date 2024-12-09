package com.tsb.exercise.accounts.entities;

import com.tsb.exercise.transactions.entities.TransactionEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.lang.Long;


@Getter
@Setter
public class AccountEntity {

    private Long id;
    private Long ownerId;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<TransactionEntity> transactions = new ArrayList<>();

    public AccountEntity(){
    }

    public AccountEntity(Long id, Long ownerId,BigDecimal balance, List<TransactionEntity> transactions){
        this.id = id;
        this.ownerId = ownerId;
        this.transactions = transactions;
        this.balance = balance;
    }

    public AccountEntity(Long id, Long ownerId){
        this.id = id;
        this.ownerId = ownerId;
    }

    public void addTransaction(TransactionEntity transaction){
        this.transactions.add(transaction);
    }

}
