package com.ankur.lld.parking_lot.factory;

import com.ankur.lld.parking_lot.enums.VehicleType;
import com.ankur.lld.parking_lot.model.Bike;
import com.ankur.lld.parking_lot.model.Car;
import com.ankur.lld.parking_lot.model.Truck;
import com.ankur.lld.parking_lot.model.Vehicle;

public class VehicleFactory {
    public static Vehicle create(String license, VehicleType type){
        return switch (type){
            case BIKE -> new Bike(license);
            case CAR -> new Car(license);
            case TRUCK -> new Truck(license);
        };
    }
}
