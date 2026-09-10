package com.ankur.lld.atm.factory;

import com.ankur.lld.atm.bank.BankService;
import com.ankur.lld.atm.cash.CashInventory;
import com.ankur.lld.atm.model.Card;
import com.ankur.lld.atm.transaction.BalanceInquiryTransaction;
import com.ankur.lld.atm.transaction.DepositTransaction;
import com.ankur.lld.atm.transaction.Transaction;
import com.ankur.lld.atm.transaction.WithdrawalTransaction;
import com.ankur.lld.atm.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionFactory {
    public static Transaction create(TransactionType type, Card card, BigDecimal amount, BankService bankService, CashInventory cashInventory){
        return switch (type) {
            case WITHDRAW -> new WithdrawalTransaction(card, amount, bankService, cashInventory);
            case DEPOSIT -> new DepositTransaction(card, amount, bankService);
            case BALANCE_INQUIRY -> new BalanceInquiryTransaction(card, bankService);
            default -> throw new IllegalArgumentException("Unsupported Transaction Type");
        };
    }
}
