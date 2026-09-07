package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.VehicleType;

public class Truck extends Vehicle{
    public Truck(String licensePlate){
        super(licensePlate, VehicleType.TRUCK);
    }
}
