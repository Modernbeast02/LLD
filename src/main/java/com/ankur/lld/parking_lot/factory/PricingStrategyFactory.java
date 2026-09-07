package com.ankur.lld.parking_lot.factory;

import com.ankur.lld.parking_lot.enums.PricingStrategyType;
import com.ankur.lld.parking_lot.strategy.pricing.EventBasedPricingStrategy;
import com.ankur.lld.parking_lot.strategy.pricing.HourBasedPricingStrategy;
import com.ankur.lld.parking_lot.strategy.pricing.PricingStrategy;

public class PricingStrategyFactory {
    public static PricingStrategy get(PricingStrategyType pricingStrategy){
        return switch(pricingStrategy){
            case HOUR_BASED -> new HourBasedPricingStrategy();
            case EVENT_BASED -> new EventBasedPricingStrategy();
        };
    }

}
