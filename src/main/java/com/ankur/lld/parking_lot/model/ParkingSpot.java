package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.ParkingSpotStatus;
import com.ankur.lld.parking_lot.enums.ParkingSpotType;

public class ParkingSpot {
    private final String id;
    private final ParkingSpotType type;

    private ParkingSpotStatus parkingSpotStatus;
    private Vehicle vehicle;

    public ParkingSpot(String id, ParkingSpotType type){
        this.id = id;
        this.type = type;
        this.parkingSpotStatus = ParkingSpotStatus.AVAILABLE;
    }

    public boolean isSpotAvailable(){
        return parkingSpotStatus == ParkingSpotStatus.AVAILABLE;
    }

    public void park(Vehicle vehicle){
        if(!isSpotAvailable()){
            throw new IllegalStateException("Spot Not Available");
        }

        this.vehicle = vehicle;
        this.parkingSpotStatus = ParkingSpotStatus.OCCUPIED;
    }

    public void vacate(){
        if(isSpotAvailable()){
            throw new IllegalStateException("Spot Already Empty");
        }

        this.vehicle = null;
        this.parkingSpotStatus = ParkingSpotStatus.AVAILABLE;
    }

    public String getParkingSpotId(){
        return id;
    }

    public ParkingSpotType getParkingSpotType(){
        return type;
    }

    public ParkingSpotStatus getParkingSpotStatus() {
        return parkingSpotStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
