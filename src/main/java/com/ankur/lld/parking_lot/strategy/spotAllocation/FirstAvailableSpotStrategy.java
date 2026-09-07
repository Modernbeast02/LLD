package com.ankur.lld.parking_lot.strategy.spotAllocation;

import com.ankur.lld.parking_lot.enums.ParkingSpotType;
import com.ankur.lld.parking_lot.enums.VehicleType;
import com.ankur.lld.parking_lot.model.ParkingFloor;
import com.ankur.lld.parking_lot.model.ParkingLot;
import com.ankur.lld.parking_lot.model.ParkingSpot;
import com.ankur.lld.parking_lot.model.Vehicle;

public class FirstAvailableSpotStrategy implements SpotAllocationStrategy{
    @Override
    public ParkingSpot findSpot(Vehicle vehicle, ParkingLot parkingLot) {
        for(ParkingFloor floor : parkingLot.getParkingFloors()){
            for(ParkingSpot spot : floor.getParkingSpots()){
                if(spot.isSpotAvailable() && canFit(vehicle, spot)){
                    return spot;
                }
            }
        }
         throw new IllegalStateException("Suitable Parking Spot Not Available");
    }
    public boolean canFit(Vehicle vehicle, ParkingSpot spot){
        VehicleType vehicleType = vehicle.getVehicleType();
        ParkingSpotType spotType = spot.getParkingSpotType();

        return switch (vehicleType){
            case BIKE -> spotType == ParkingSpotType.MOTORCYCLE || spotType == ParkingSpotType.COMPACT || spotType == ParkingSpotType.LARGE;
            case CAR -> spotType == ParkingSpotType.COMPACT || spotType == ParkingSpotType.LARGE;
            case TRUCK -> spotType == ParkingSpotType.LARGE;
            default -> false;
        };
    }
}
