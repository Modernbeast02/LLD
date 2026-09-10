package com.ankur.lld.atm.state;

import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public class CardInsertedState implements ATMState {
    private final ATM atm;

    public CardInsertedState(ATM atm){
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        System.out.println("A card is already inserted");
    }

    @Override
    public void enterPin(String pin) {
        Card card = atm.getCard();
        boolean authenticated = atm.getBankService().authenticate(card, pin);

        if(authenticated){
            System.out.println("PIN verified successfully");

            atm.setState(new AuthenticatedState(atm));
        }
        else{
            System.out.println("Wrong Pin Entered");
        }
    }

    @Override
    public void checkBalance() {
        System.out.println("Please enter PIN first");
    }

    @Override
    public void withdraw(BigDecimal amount) {
        System.out.println("Please enter PIN first");
    }

    @Override
    public void deposit(BigDecimal amount) {
        System.out.println("Please enter PIN first");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card Ejected");
        atm.clearCard();
        atm.setState(new IdleState(atm));
    }
}
