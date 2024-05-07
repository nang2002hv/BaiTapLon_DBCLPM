package com.example.btl_dbclpm.tariff;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ElectricityTariff {
    private final Map<Integer, Double> price = new LinkedHashMap<>();

    public ElectricityTariff() {
        createTariff();
    }

    public void createTariff() {
        price.put(50, 1806.0);
        price.put(100, 1866.0);
        price.put(200, 2167.0);
        price.put(300, 2729.0);
        price.put(400, 3050.0);
        price.put(Integer.MAX_VALUE, 3151.0);
    }

    public double calculatePrice(long consumption) {
        double total = 0.0;
        if(consumption < 0) {
            return -1;
        }
        long remainingConsumption = consumption;
        int previousKey = 0;
        for (Map.Entry<Integer, Double> entry : price.entrySet()) {
            long units = Math.min(entry.getKey() - previousKey, remainingConsumption);
            total += units * entry.getValue();
            remainingConsumption -= units;
            if (remainingConsumption == 0) {
                break;
            }
            previousKey = entry.getKey();
        }
        if (remainingConsumption > 0) {
            total += remainingConsumption * price.get(Integer.MAX_VALUE);
        }
        return total;
    }
}