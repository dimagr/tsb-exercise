package com.tsb.exercise.accounts;

import com.tsb.exercise.accounts.actions.FindUserAccountsAction;
import com.tsb.exercise.accounts.dtos.UserAccountDto;
import com.tsb.exercise.accounts.entities.AccountEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountsController {

    private final FindUserAccountsAction findUserAccountsAction;

    public AccountsController(FindUserAccountsAction findUserAccountsAction){
        this.findUserAccountsAction = findUserAccountsAction;
    }

    @GetMapping("")
    public List<UserAccountDto> getAccounts() {
        List<AccountEntity> accountEntities = findUserAccountsAction.execute(101L);

        return accountEntities.stream()
                .map(account -> new UserAccountDto(
                        account.getId(),
                        account.getOwnerId(),
                        account.getBalance())
                ).collect(Collectors.toList());
    }
}
