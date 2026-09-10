package com.ankur.lld.atm.transaction;

import com.ankur.lld.atm.bank.BankService;
import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public class DepositTransaction implements Transaction{
    private final Card card;
    private final BigDecimal amount;
    private final BankService bankService;

    public DepositTransaction(Card card, BigDecimal amount, BankService bankService){
        this.card = card;
        this.amount = amount;
        this.bankService = bankService;
    }

    @Override
    public void execute() {
        bankService.deposit(card, amount);

        System.out.println("Deposit successful: ₹" + amount);
    }
}
