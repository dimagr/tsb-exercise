package com.tsb.exercise.accounts.actions;

import com.tsb.exercise.accounts.entities.AccountEntity;
import com.tsb.exercise.accounts.repositories.AccountsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindUserAccountsAction {

    private final AccountsRepository accountsRepository;

    public FindUserAccountsAction(AccountsRepository accountsRepository){
        this.accountsRepository = accountsRepository;
    }

    public List<AccountEntity> execute(Long userId){
        // validate user exists
        return accountsRepository.findAllAccountsByUserId(userId);
    }
}
