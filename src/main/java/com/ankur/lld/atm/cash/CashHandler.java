package com.ankur.lld.atm.cash;

import java.util.Map;

public abstract class CashHandler {
    protected final int denomination;
    protected int availableNotes;
    protected CashHandler next;

    protected CashHandler(int denomination, int availableNotes){
        this.denomination = denomination;
        this.availableNotes = availableNotes;
    }

    public void setNext(CashHandler next){
        this.next = next;
    }

    public int getAvailableNotes(){
        return availableNotes;
    }

    public int getDenomination(){
        return denomination;
    }

    public void removeNotes(int count){
        if(count >  availableNotes){
            throw new IllegalStateException("Not enough notes available");
        }

        availableNotes -= count;
    }


    public int dispense(int amount, Map<Integer, Integer> result){
        int notesNeeded = amount / denomination;

        int notesThatCanBeGiven = Math.min(notesNeeded, availableNotes);

        if(notesThatCanBeGiven > 0){
            result.put(denomination, notesThatCanBeGiven);
            amount -= notesThatCanBeGiven * denomination;
        }

        if(amount > 0 && next != null){
            return next.dispense(amount, result);
        }

        return amount;
    }

}
