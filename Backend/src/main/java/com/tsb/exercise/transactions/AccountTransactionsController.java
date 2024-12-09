package com.tsb.exercise.transactions;

import com.tsb.exercise.transactions.actions.FindAccountTransactionsAction;
import com.tsb.exercise.transactions.actions.SendMoneyToAccountAction;
import com.tsb.exercise.transactions.entities.TransactionEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountTransactionsController {


    private final FindAccountTransactionsAction findAccountTransactionsAction;
    private final SendMoneyToAccountAction sendMoneyToAccountAction;

    public AccountTransactionsController(FindAccountTransactionsAction findAccountTransactionsAction, SendMoneyToAccountAction sendMoneyToAccountAction){
        this.findAccountTransactionsAction = findAccountTransactionsAction;
        this.sendMoneyToAccountAction = sendMoneyToAccountAction;
    }

    @GetMapping("")
    public List<TransactionEntity> getTransactions(@PathVariable Long accountId){
        return findAccountTransactionsAction.execute(101L, accountId);
    }

    @PostMapping
    public void createTransaction(@PathVariable Long accountId, @RequestBody TransactionEntity transaction){
        if(!Objects.equals(transaction.fromAccount(), accountId)){
            throw new IllegalArgumentException("Account id dose not match ");
        }
        sendMoneyToAccountAction.execute(accountId,101L, transaction);

    }
}
