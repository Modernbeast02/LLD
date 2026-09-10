package com.ankur.lld.atm.bank;

import com.ankur.lld.atm.model.Account;
import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryBankService implements BankService {

    private final Map<String, Account> accounts;
    private final Map<String, String> cardToPin;
    private final Map<String, String> cardToAccount;

    public InMemoryBankService(){
        accounts = new HashMap<>();
        cardToPin = new HashMap<>();
        cardToAccount = new HashMap<>();
    }

    public void addAccount(Card card, String pin, Account account){
        accounts.put(account.getAccountNumber(), account);
        cardToPin.put(card.getCardNumber(), pin);
        cardToAccount.put(card.getCardNumber(), account.getAccountNumber());
    }

    private Account getAccount(Card card){
        String accountNumber = cardToAccount.get(card.getCardNumber());
        if(accountNumber == null){
            throw new IllegalStateException("Account not found");
        }

        Account account = accounts.get(accountNumber);
        if(account == null){
            throw new IllegalStateException("Account not found");
        }

        return account;
    }
    @Override
    public boolean authenticate(Card card, String pin) {

        String realPin = cardToPin.get(card.getCardNumber());

        return realPin != null && realPin.equals(pin);
    }

    @Override
    public BigDecimal getBalance(Card card) {

        Account account = getAccount(card);
        return account.getBalance();
    }

    @Override
    public void withdraw(Card card, BigDecimal amount) {
        Account account = getAccount(card);
        account.withdraw(amount);
    }

    @Override
    public void deposit(Card card, BigDecimal amount) {
        Account account = getAccount(card);
        account.deposit(amount);
    }
}
