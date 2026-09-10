package com.ankur.lld.atm.model;

import java.math.BigDecimal;

public class Account {
    private final String accountNumber;
    private BigDecimal balance;
    public Account(String accountNumber, BigDecimal balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }
    public BigDecimal getBalance(){
        return balance;
    }

    public synchronized void withdraw(BigDecimal amount){
        if(amount.compareTo(balance) > 0){
            throw new IllegalStateException("Not enough money in account");
        }
        balance = balance.subtract(amount);
    }

    public synchronized void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }

}
