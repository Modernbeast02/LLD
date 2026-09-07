package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.GateType;

public class ExitGate extends Gate{
    public ExitGate(String id){
        super(id, GateType.EXIT);
    }
}
