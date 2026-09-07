package com.ankur.lld.parking_lot.strategy.payment;

import com.ankur.lld.parking_lot.enums.PaymentStatus;

public interface PaymentStrategy {
    PaymentStatus pay(int amount);
}
