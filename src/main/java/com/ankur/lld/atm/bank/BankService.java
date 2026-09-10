package com.ankur.lld.atm.bank;

import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public interface BankService {
    boolean authenticate(Card card, String pin);

    BigDecimal getBalance(Card card);

    void withdraw(Card card, BigDecimal amount);

    void deposit(Card card, BigDecimal amount);


}
