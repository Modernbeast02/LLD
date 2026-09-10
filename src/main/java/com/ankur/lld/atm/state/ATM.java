package com.ankur.lld.atm.state;

import com.ankur.lld.atm.bank.BankService;
import com.ankur.lld.atm.cash.CashInventory;
import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public class ATM {
    private ATMState state;
    private Card card;

    private final BankService bankService;
    private final CashInventory cashInventory;

    public ATM(BankService service, CashInventory inventory){
        this.bankService = service;
        this.cashInventory = inventory;
        this.state = new IdleState(this);
    }

    public void insertCard(Card card){
        state.insertCard(card);
    }
    public void enterPin(String pin){
        state.enterPin(pin);
    }

    public void checkBalance(){
        state.checkBalance();
    }

    public void withdraw(BigDecimal amount){
        state.withdraw(amount);
    }
    public void deposit(BigDecimal amount){
        state.deposit(amount);
    }

    public void ejectCard(){
        state.ejectCard();
    }
    public void setState(ATMState state) {
        this.state = state;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void clearCard() {
        this.card = null;
    }

    public BankService getBankService() {
        return bankService;
    }

    public CashInventory getCashInventory() {
        return cashInventory;
    }
}
