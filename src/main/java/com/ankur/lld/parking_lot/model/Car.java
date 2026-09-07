package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.VehicleType;

public class Car extends Vehicle {
    public Car(String licensePlate){
        super(licensePlate, VehicleType.CAR);
    }
}
