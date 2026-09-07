package com.ankur.lld.parking_lot;

import com.ankur.lld.parking_lot.enums.*;
import com.ankur.lld.parking_lot.factory.PaymentStrategyFactory;
import com.ankur.lld.parking_lot.factory.PricingStrategyFactory;
import com.ankur.lld.parking_lot.factory.VehicleFactory;
import com.ankur.lld.parking_lot.model.*;
import com.ankur.lld.parking_lot.service.ParkingService;
import com.ankur.lld.parking_lot.strategy.pricing.PricingStrategy;
import com.ankur.lld.parking_lot.strategy.spotAllocation.FirstAvailableSpotStrategy;

public class Main {

    public static void main(String[] args) {
        // Create Parking Lot
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1");

        // Creating Floors
        ParkingFloor floor1 = new ParkingFloor(1);

        // Add spots to the floor
        floor1.addParkingSpot(new ParkingSpot("F1-M1", ParkingSpotType.MOTORCYCLE));
        floor1.addParkingSpot(new ParkingSpot("F1-C1", ParkingSpotType.COMPACT));
        floor1.addParkingSpot(new ParkingSpot("F1-L1", ParkingSpotType.LARGE));
        floor1.addParkingSpot(new ParkingSpot("F1-C2", ParkingSpotType.COMPACT));

        // Add floor to the parking lot
        parkingLot.addFloors(floor1);

        ParkingFloor floor2 = new ParkingFloor(2);

        // Add spots to the floor
        floor2.addParkingSpot(new ParkingSpot("F2-M1", ParkingSpotType.MOTORCYCLE));
        floor2.addParkingSpot(new ParkingSpot("F2-C1", ParkingSpotType.COMPACT));
        floor2.addParkingSpot(new ParkingSpot("F2-L1", ParkingSpotType.LARGE));

        // Add floor to the parking lot
        parkingLot.addFloors(floor2);

        EntryGate entryGate = new EntryGate("EntryGate1");

        FirstAvailableSpotStrategy allocationStrategy = new FirstAvailableSpotStrategy();

        // Factory for pricing strategy
        PricingStrategy pricingStrategy = PricingStrategyFactory.get(PricingStrategyType.EVENT_BASED);

        ParkingService parkingService = new ParkingService(parkingLot, allocationStrategy, pricingStrategy);

        Vehicle car = VehicleFactory.create("KA-01-HH-1234", VehicleType.CAR);

        // Ticket
        ParkingTicket ticket = parkingService.parkVehicle(car, entryGate);

        // Exiting

        parkingService.exitVehicle(ticket.getTicketId(), PaymentStrategyFactory.get(PaymentMode.UPI));

        System.out.println("Spot available: " + ticket.getParkingSpot().isSpotAvailable());

    }
}