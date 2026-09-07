package com.ankur.lld.parking_lot.strategy.pricing;

import com.ankur.lld.parking_lot.model.ParkingTicket;

import java.time.LocalDateTime;

public interface PricingStrategy {
    int calculatePrice(ParkingTicket ticket, LocalDateTime exitTime);
}
