package com.ankur.lld.parking_lot.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    private final int floorNumber;
    private List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber){
        this.floorNumber = floorNumber;
        this.parkingSpots = new ArrayList<>();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getParkingSpots(){
        return parkingSpots;
    }

    public void addParkingSpot(ParkingSpot spot){
        parkingSpots.add(spot);
    }
}
