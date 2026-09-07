package com.ankur.lld.parking_lot.strategy.payment;

import com.ankur.lld.parking_lot.enums.PaymentStatus;

import java.math.BigDecimal;

public class UpiPaymentStrategy implements PaymentStrategy{
    @Override
    public PaymentStatus pay(int amount) {
        System.out.println("Paid Rs " + amount + " via UPI");
        return PaymentStatus.SUCCESS;
    }
}
