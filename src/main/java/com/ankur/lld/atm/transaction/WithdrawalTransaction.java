package com.ankur.lld.atm.transaction;

import com.ankur.lld.atm.bank.BankService;
import com.ankur.lld.atm.cash.CashInventory;
import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;
import java.util.Map;

public class WithdrawalTransaction implements Transaction{

    private final Card card;
    private final BigDecimal amount;
    private final BankService bankService;
    private final CashInventory cashInventory;

    public WithdrawalTransaction(Card card, BigDecimal amount, BankService bankService, CashInventory cashInventory){
        this.card = card;
        this.amount = amount;
        this.bankService = bankService;
        this.cashInventory = cashInventory;
    }

    @Override
    public void execute() {
        // Checking whether atm can give the cash
        Map<Integer, Integer> cash = cashInventory.dispense(amount.intValue());

        // If yes then debiting the account
        bankService.withdraw(card, amount);

        System.out.println("Dispensed cash: " + cash);

        System.out.println("Withdrawal successful: ₹" + amount);
    }
}
