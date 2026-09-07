package com.ankur.lld.parking_lot.strategy.payment;

import com.ankur.lld.parking_lot.enums.PaymentStatus;

import java.math.BigDecimal;

public class CardPaymentStrategy implements PaymentStrategy{
    @Override
    public PaymentStatus pay(int amount) {
        System.out.println("Paid Rs " + amount + " via Card");
        return PaymentStatus.SUCCESS;
    }
}
