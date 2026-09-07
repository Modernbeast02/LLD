package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.TicketStatus;

import java.time.LocalDateTime;

public class ParkingTicket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final EntryGate entryGate;
    private final LocalDateTime entryTime;

    private TicketStatus ticketStatus;

    public ParkingTicket(String id, Vehicle vehicle, ParkingSpot parkingSpot, EntryGate entryGate){
        this.ticketId = id;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryGate = entryGate;
        this.entryTime = LocalDateTime.now();
        this.ticketStatus = TicketStatus.ACTIVE;
    }

    public void markTicketPaid(){
        if(ticketStatus != TicketStatus.ACTIVE){
            throw new IllegalStateException("Ticket is not active");
        }
        ticketStatus = TicketStatus.PAID;
    }

    public void closeTicket(){
        if(ticketStatus != TicketStatus.PAID){
            throw new IllegalStateException("Ticket is not paid yet");
        }

        ticketStatus = TicketStatus.CLOSED;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public EntryGate getEntryGate() {
        return entryGate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }
}
