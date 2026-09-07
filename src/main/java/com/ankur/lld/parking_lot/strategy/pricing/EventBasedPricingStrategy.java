package com.ankur.lld.parking_lot.strategy.pricing;

import com.ankur.lld.parking_lot.model.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Math.max;

public class EventBasedPricingStrategy implements PricingStrategy{

    @Override
    public int calculatePrice(ParkingTicket ticket, LocalDateTime exitTime) {

        LocalDateTime entryTime = ticket.getEntryTime();

        if(exitTime.isBefore(entryTime)){
            throw new IllegalArgumentException("Exit Time cannot be before Entry time");
        }

        long durationMinutes = Duration.between(entryTime, exitTime).toMinutes();
        long totalHours = (long) Math.ceil(durationMinutes / 60.0);

        int eventRate = switch(ticket.getVehicle().getVehicleType()){
            case BIKE -> 20;
            case CAR -> 30;
            case TRUCK -> 40;
        };

        return (int) (eventRate * max(1, totalHours));
    }
}
