package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.VehicleType;

public class Bike extends Vehicle {
    public Bike(String licensePlate){
        super(licensePlate, VehicleType.BIKE);
    }
}
