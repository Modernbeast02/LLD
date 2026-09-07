package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.GateType;

public class Gate {
    private final String id;

    private final GateType type;

    public Gate(String id, GateType type) {
        this.id = id;
        this.type = type;
    }

    public String getGateId(){
        return id;
    }

    public GateType getGateType(){
        return type;
    }
}
