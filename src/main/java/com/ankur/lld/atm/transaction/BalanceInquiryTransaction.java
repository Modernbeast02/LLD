package com.ankur.lld.atm.transaction;

import com.ankur.lld.atm.bank.BankService;
import com.ankur.lld.atm.model.Card;

import java.math.BigDecimal;

public class BalanceInquiryTransaction implements Transaction{
    private final Card card;
    private final BankService bankService;

    public BalanceInquiryTransaction(Card card, BankService bankService){
        this.card = card;
        this.bankService = bankService;
    }

    @Override
    public void execute(){
        BigDecimal balance = bankService.getBalance(card);
        System.out.println("Current balance: ₹" + balance);
    }

}
