package com.ankur.lld.atm.state;

import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public interface ATMState {

    void insertCard(Card card);

    void enterPin(String pin);

    void checkBalance();

    void withdraw(BigDecimal amount);

    void deposit(BigDecimal amount);

    void ejectCard();
}
