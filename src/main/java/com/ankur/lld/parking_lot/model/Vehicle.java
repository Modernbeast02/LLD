package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.VehicleType;

public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type){
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public VehicleType getVehicleType(){
        return type;
    }


}
