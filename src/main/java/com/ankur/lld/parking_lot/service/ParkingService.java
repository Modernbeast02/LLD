package com.ankur.lld.parking_lot.service;

import com.ankur.lld.parking_lot.enums.PaymentStatus;
import com.ankur.lld.parking_lot.enums.TicketStatus;
import com.ankur.lld.parking_lot.model.*;
import com.ankur.lld.parking_lot.strategy.payment.PaymentStrategy;
import com.ankur.lld.parking_lot.strategy.pricing.PricingStrategy;
import com.ankur.lld.parking_lot.strategy.spotAllocation.SpotAllocationStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkingService {
    private final ParkingLot parkingLot;
    private final SpotAllocationStrategy spotAllocationStrategy;
    private final PricingStrategy pricingStrategy;
    private final Map<String, ParkingTicket> tickets;

    public ParkingService(ParkingLot parkingLot, SpotAllocationStrategy spotAllocationStrategy, PricingStrategy pricingStrategy){
        this.parkingLot = parkingLot;
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.pricingStrategy = pricingStrategy;
        this.tickets = new HashMap<>();
    }

    public ParkingTicket parkVehicle(Vehicle vehicle, EntryGate entryGate){

        // Find a spot, park vehicle, generate ticket, add ticket in hashmap

        ParkingSpot parkingSpot = spotAllocationStrategy.findSpot(vehicle, parkingLot);

        parkingSpot.park(vehicle);

        String ticketId = UUID.randomUUID().toString();

        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, parkingSpot, entryGate);

        tickets.put(ticketId, ticket);

        System.out.println("Vehicle " + vehicle.getLicensePlate() + " parked at Spot " + parkingSpot.getParkingSpotId());

        System.out.println("Ticket generated: " + ticketId);

        return ticket;
    }

    public int calculateTicketFee(String ticketId){
        ParkingTicket ticket = getTicket(ticketId);
        validateActiveTicket(ticket);
        return pricingStrategy.calculatePrice(ticket, LocalDateTime.now());
    }

    public void exitVehicle(String ticketId, PaymentStrategy paymentStrategy){
        ParkingTicket ticket = getTicket(ticketId);

        int fee = calculateTicketFee(ticketId);
        System.out.println("Parking Fees " + fee);

        PaymentStatus paymentStatus = paymentStrategy.pay(fee);

        if (paymentStatus != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Payment failed");
        }

        ticket.markTicketPaid();

        ticket.getParkingSpot().vacate();

        ticket.closeTicket();

        System.out.println("Vehicle " + ticket.getVehicle().getLicensePlate() + " exited successfully");
    }
    private ParkingTicket getTicket(String ticketId){
        ParkingTicket ticket = tickets.get(ticketId);

        if(ticket == null){
            throw new IllegalStateException("Invalid Ticket Id");
        }

        return ticket;
    }
    private void validateActiveTicket(ParkingTicket ticket) {
        if (ticket.getTicketStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket is not active");
        }
    }
}
