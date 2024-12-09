package com.tsb.exercise.transactions.actions;

import com.tsb.exercise.accounts.entities.AccountEntity;
import com.tsb.exercise.accounts.repositories.AccountsRepository;
import com.tsb.exercise.transactions.entities.TransactionEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class FindAccountTransactionsAction {

    private final AccountsRepository accountsRepository;

    public FindAccountTransactionsAction(AccountsRepository accountsRepository){
        this.accountsRepository = accountsRepository;
    }

    public List<TransactionEntity> execute(Long userId, Long accountId){

        AccountEntity account = accountsRepository.findAccountByIdAndUserId(accountId, userId);

        if(CollectionUtils.isEmpty(account.getTransactions())){
            return Collections.emptyList();
        }

        return account.getTransactions();
    }

}
