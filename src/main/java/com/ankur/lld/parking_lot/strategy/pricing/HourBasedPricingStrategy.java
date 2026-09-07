package com.ankur.lld.parking_lot.strategy.pricing;

import com.ankur.lld.parking_lot.model.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class HourBasedPricingStrategy implements PricingStrategy{

    private static final LocalTime PEAK_START = LocalTime.of(8, 0);
    private static final LocalTime PEAK_END = LocalTime.of(17, 0);

    private boolean isPeak(LocalTime time) {
        return !time.isBefore(PEAK_START) && !time.isAfter(PEAK_END);
    }
    @Override
    public int calculatePrice(ParkingTicket ticket, LocalDateTime exitTime) {
        LocalDateTime entryTime = ticket.getEntryTime();

        if(exitTime.isBefore(entryTime)){
            throw new IllegalArgumentException("Exit Time cannot be before Entry time");
        }

        long durationMinutes = Duration.between(entryTime, exitTime).toMinutes();
        long totalHours = (long) Math.ceil(durationMinutes / 60.0);

        int peakHours = 0;
        int nonPeakHours = 0;

        LocalDateTime cursor = entryTime.truncatedTo(ChronoUnit.HOURS);

        for (int i = 0; i < totalHours; i++) {
            LocalTime hourStart = cursor.toLocalTime();
            if (isPeak(hourStart)) {
                peakHours++;
            }
            else{
                nonPeakHours++;
            }
            cursor = cursor.plusHours(1);
        }

        int peakRate = switch(ticket.getVehicle().getVehicleType()){
            case BIKE -> 20;
            case CAR -> 30;
            case TRUCK -> 40;
        };

        int nonPeakRate = switch(ticket.getVehicle().getVehicleType()){
            case BIKE -> 10;
            case CAR -> 20;
            case TRUCK -> 30;
        };

        return peakHours * peakRate + nonPeakHours * nonPeakRate;

    }
}
