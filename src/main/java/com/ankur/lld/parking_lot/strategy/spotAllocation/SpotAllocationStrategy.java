package com.ankur.lld.parking_lot.strategy.spotAllocation;

import com.ankur.lld.parking_lot.model.ParkingLot;
import com.ankur.lld.parking_lot.model.ParkingSpot;
import com.ankur.lld.parking_lot.model.Vehicle;

public interface SpotAllocationStrategy {
    ParkingSpot findSpot(Vehicle vehicle, ParkingLot parkingLot);
}
