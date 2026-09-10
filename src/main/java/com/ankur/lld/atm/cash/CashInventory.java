package com.ankur.lld.atm.cash;

import java.util.HashMap;
import java.util.Map;

public class CashInventory {
    private final CashHandler firstHandler;

    public CashInventory(int fiveHunderNotes, int twoHundredNotes, int oneHundredNotes){
        CashHandler fiveHundredHandler = new FiveHundredHandler(fiveHunderNotes);
        CashHandler twoHundredHandler = new TwoHundredHandler(twoHundredNotes);
        CashHandler oneHundredHandler = new OneHundredHandler(oneHundredNotes);

        fiveHundredHandler.setNext(twoHundredHandler);
        twoHundredHandler.setNext(oneHundredHandler);
        firstHandler = fiveHundredHandler;
    }

    public synchronized Map<Integer, Integer> dispense(int amount){
        if(amount < 0){
            throw new IllegalStateException("Amount cannot be negative");
        }

        Map<Integer, Integer> result = new HashMap<>();

        int remainingAmount = firstHandler.dispense(amount, result);
        if(remainingAmount != 0){
            throw new IllegalStateException("Cannot dispense requested amount");
        }

        for(Map.Entry<Integer, Integer> entry: result.entrySet()){
            int denomination = entry.getKey();
            int countOfNotes = entry.getValue();

            removeNotes(denomination, countOfNotes);
        }

        return result;
    }

    private void removeNotes(int denomination, int count){
        CashHandler currentHandler = firstHandler;

        while(currentHandler != null){
            if(currentHandler.getDenomination() == denomination){
                currentHandler.removeNotes(count);
                return;
            }
            currentHandler = currentHandler.next;
        }
        throw new IllegalStateException("Denomination not found");
    }

}
