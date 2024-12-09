package com.tsb.exercise.transactions.actions;

import com.tsb.exercise.accounts.repositories.AccountsRepository;
import com.tsb.exercise.transactions.entities.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public class SendMoneyToAccountAction {

    private final AccountsRepository accountsRepository;

    public SendMoneyToAccountAction( AccountsRepository accountsRepository){
        this.accountsRepository = accountsRepository;
    }

    public void execute(Long fromAccountId, Long UserId,TransactionEntity transaction) {
        accountsRepository.createTransactionByAccountIdAndUserId(fromAccountId, UserId, transaction);
    }
}
