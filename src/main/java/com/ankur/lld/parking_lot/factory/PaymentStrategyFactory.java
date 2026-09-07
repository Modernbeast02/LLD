package com.ankur.lld.parking_lot.factory;

import com.ankur.lld.parking_lot.enums.PaymentMode;
import com.ankur.lld.parking_lot.strategy.payment.CardPaymentStrategy;
import com.ankur.lld.parking_lot.strategy.payment.CashPaymentStrategy;
import com.ankur.lld.parking_lot.strategy.payment.PaymentStrategy;
import com.ankur.lld.parking_lot.strategy.payment.UpiPaymentStrategy;

public class PaymentStrategyFactory {
    public static PaymentStrategy get(PaymentMode paymentMode){
        return switch (paymentMode){
            case UPI -> new UpiPaymentStrategy();
            case CARD -> new CardPaymentStrategy();
            case CASH -> new CashPaymentStrategy();
        };
    }
}
