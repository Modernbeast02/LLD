package com.ankur.lld.atm;

import com.ankur.lld.atm.bank.InMemoryBankService;
import com.ankur.lld.atm.cash.CashInventory;
import com.ankur.lld.atm.model.Account;
import com.ankur.lld.atm.model.Card;
import com.ankur.lld.atm.state.ATM;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){
        Card card = new Card("123456");
        Account account = new Account("12345", new BigDecimal(10000));

        InMemoryBankService bankService = new InMemoryBankService();

        //Adding account in bank with pin
        bankService.addAccount(card, "1234", account);

        // Creating the atm cash inventory
        CashInventory cashInventory = new CashInventory(
                10,  // ₹500 notes
                10,  // ₹200 notes
                20   // ₹100 notes
        );

        ATM atm = new ATM(bankService, cashInventory);

        // Flow

        atm.insertCard(card);

        atm.enterPin("1234");

        atm.withdraw(new BigDecimal(9000));

        atm.checkBalance();

        atm.ejectCard();


    }
}
