package com.ankur.lld.parking_lot.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final String id;
    private final List<ParkingFloor> floors;

    public ParkingLot(String id){
        this.id = id;
        this.floors = new ArrayList<>();
    }

    public void addFloors(ParkingFloor floor){
        floors.add(floor);
    }
    public String getParkingLotId(){
        return id;
    }

    public List<ParkingFloor> getParkingFloors(){
        return floors;
    }
}
