package com.tsb.exercise.accounts.repositories;

import com.tsb.exercise.accounts.entities.AccountEntity;
import com.tsb.exercise.transactions.entities.TransactionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;


@Component
public class AccountsRepository {

    private static final List<TransactionEntity> transactions1 = new ArrayList<>(List.of(
            new TransactionEntity(1000L, new Date(), new BigDecimal(100), 1L, 2L),
            new TransactionEntity(1001L, new Date(), new BigDecimal(102), 1L, 3L),
            new TransactionEntity(1002L, new Date(), new BigDecimal(130), 4L, 1L),
            new TransactionEntity(1003L, new Date(), new BigDecimal(-100), 2L, 1L)
    ));

    //represent DB
    private static final List<AccountEntity> accounts = new ArrayList<>(List.of(
            new AccountEntity(1L, 101L, new BigDecimal(100), transactions1),
            new AccountEntity(2L, 101L, new BigDecimal(1050), new ArrayList<>()),
            new AccountEntity(3L, 101L, new BigDecimal(385), new ArrayList<>()),
            new AccountEntity(4L, 101L, new BigDecimal(2396), new ArrayList<>()),
            new AccountEntity(5L, 101L, new BigDecimal(3973), new ArrayList<>()),
            new AccountEntity(6L, 101L, new BigDecimal("67.54"), new ArrayList<>()),
            new AccountEntity(7L, 101L, new BigDecimal(9346), new ArrayList<>()),
            new AccountEntity(8L, 101L, new BigDecimal("9567.44"), new ArrayList<>()),
            new AccountEntity(9L, 101L, new BigDecimal(101), new ArrayList<>())
    ));


    public List<AccountEntity> findAllAccountsByUserId(Long userId){
        return accounts.stream().filter(a -> Objects.equals(a.getOwnerId(), userId)).toList();
    }

    public AccountEntity findAccountByIdAndUserId(Long accountId, Long userId){

        Optional<AccountEntity> accountEntity = accounts.stream()
                .filter(a -> Objects.equals(a.getId(), accountId)
                        && Objects.equals(a.getOwnerId(), userId))
                .findFirst();
        return accountEntity.orElse(null);
    }

    public void createTransactionByAccountIdAndUserId(Long accountId, Long userId, TransactionEntity transaction){
        // validate sending and receiving account exist for the user
        AccountEntity fromAccount = findAccountByIdAndUserId(accountId, userId);
        if(fromAccount == null){
            throw new IllegalArgumentException("from account not found");
        }
        AccountEntity toAccount = findAccountByIdAndUserId(transaction.toAccountId(), userId);
        if(toAccount == null){
            throw new IllegalArgumentException("to account not found");
        }

        synchronized(fromAccount){
            synchronized(toAccount){
                fromAccount.setBalance(fromAccount.getBalance().abs().subtract(transaction.amount()));
                toAccount.setBalance(fromAccount.getBalance().add(transaction.amount()));
                fromAccount.addTransaction(new TransactionEntity(maxTransactionId() + 1, transaction));
                toAccount.getTransactions().add(new TransactionEntity(maxTransactionId() + 1, transaction));
            }
        }

    }

    private Long maxTransactionId(){
        return accounts.stream()
                .flatMap(account -> account.getTransactions().stream())
                .map(TransactionEntity::id)
                .max(Long::compare)
                .orElse(null);
    }


}
