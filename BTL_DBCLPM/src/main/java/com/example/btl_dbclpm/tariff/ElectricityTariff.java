package com.example.btl_dbclpm.tariff;

import com.example.btl_dbclpm.model.AmountByStep;

import java.util.*;

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

    public List<AmountByStep> calculatePrice(long consumption) {
        List<AmountByStep> result = new ArrayList<>();
        int step = 1;
        if(consumption <= 0) {
            return result;
        }
        long remainingConsumption = consumption;
        int previousKey = 0;
        for (Map.Entry<Integer, Double> entry : price.entrySet()) {
            long units = Math.min(entry.getKey() - previousKey, remainingConsumption);
            remainingConsumption -= units;
            AmountByStep amountByStep = AmountByStep.builder()
                    .step(step)
                    .price(entry.getValue())
                    .consumption(units)
                    .amount(units * entry.getValue())
                    .build();
            result.add(amountByStep);
            step++;
            if (remainingConsumption == 0) {
                break;
            }
            previousKey = entry.getKey();
        }
        if (remainingConsumption > 0) {
            AmountByStep lastStep = result.get(result.size() - 1);
            lastStep.setConsumption(lastStep.getConsumption() + remainingConsumption);
            lastStep.setAmount(lastStep.getAmount() + remainingConsumption * lastStep.getPrice());
        }
        return result;
    }
}