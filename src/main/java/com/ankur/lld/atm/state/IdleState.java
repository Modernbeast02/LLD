package com.ankur.lld.atm.state;

import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public class IdleState implements ATMState{

    private final ATM atm;

    public IdleState(ATM atm){
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        atm.setCard(card);
        System.out.println("Card inserted successfully");
        atm.setState(new CardInsertedState(atm));
    }

    @Override
    public void enterPin(String pin) {
        System.out.println("Please insert card first");
    }

    @Override
    public void checkBalance() {
        System.out.println("Please insert card first");
    }

    @Override
    public void withdraw(BigDecimal amount) {
        System.out.println("Please insert card first");
    }

    @Override
    public void deposit(BigDecimal amount) {
        System.out.println("Please insert card first");
    }

    @Override
    public void ejectCard() {
        System.out.println("Please insert card first");
    }
}
