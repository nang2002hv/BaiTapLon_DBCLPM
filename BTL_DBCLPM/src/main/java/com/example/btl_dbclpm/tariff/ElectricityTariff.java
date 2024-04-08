package com.example.btl_dbclpm.tariff;

import java.util.HashMap;
import java.util.Map;

public class ElectricityTariff {
    private Map<Integer, Double> price = new HashMap<>();

    public ElectricityTariff() {
        createTariff();
    }

    public Map<Integer, Double> getPrice() {
        return price;
    }

    public void createTariff() {
        price.put(50, 1806.0);
        price.put(100, 1866.0);
        price.put(200, 2167.0);
        price.put(300, 2729.0);
        price.put(400, 3050.0);
        price.put(500, 0.0);
    }

    public double calculatePrice(long consumption) {
        double total = 0.0;
        long remainingConsumption = consumption;
        for (Map.Entry<Integer, Double> entry : price.entrySet()) {
            if (remainingConsumption > entry.getKey()) {
                total += (entry.getKey() * entry.getValue());
                remainingConsumption -= entry.getKey();
            } else {
                total += (remainingConsumption * entry.getValue());
                break;
            }
        }
        return total;
    }
}