package com.ankur.lld.parking_lot.model;

import com.ankur.lld.parking_lot.enums.GateType;

public class EntryGate extends Gate{
    public EntryGate(String id){
        super(id, GateType.ENTRY);
    }
}
