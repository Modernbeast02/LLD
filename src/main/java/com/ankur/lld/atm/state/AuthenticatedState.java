package com.ankur.lld.atm.state;

import com.ankur.lld.atm.model.Card;
import com.ankur.lld.atm.transaction.Transaction;
import com.ankur.lld.atm.enums.TransactionType;
import com.ankur.lld.atm.factory.TransactionFactory;

import java.math.BigDecimal;

public class AuthenticatedState implements ATMState{

    private final ATM atm;

    public AuthenticatedState(ATM atm){
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        System.out.println("A card is already inserted");
    }

    @Override
    public void enterPin(String pin) {
        System.out.println("Authentication already done");
    }

    @Override
    public void checkBalance() {
        Card card = atm.getCard();

        Transaction transaction = TransactionFactory.create(TransactionType.BALANCE_INQUIRY, card, null, atm.getBankService(), atm.getCashInventory());

        transaction.execute();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        Card card = atm.getCard();

        Transaction transaction = TransactionFactory.create(TransactionType.WITHDRAW, card, amount, atm.getBankService(), atm.getCashInventory());

        transaction.execute();
    }

    @Override
    public void deposit(BigDecimal amount) {
        Card card = atm.getCard();

        Transaction transaction = TransactionFactory.create(TransactionType.DEPOSIT, card, amount, atm.getBankService(), atm.getCashInventory());

        transaction.execute();
    }

    @Override
    public void ejectCard() {
        System.out.println("Card Ejected");
        atm.clearCard();
        atm.setState(new IdleState(atm));
    }
}
