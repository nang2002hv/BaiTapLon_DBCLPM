package com.example.btl_dbclpm.tariff;

import com.example.btl_dbclpm.model.AmountByStep;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectricityTariffTest {
    private final ElectricityTariff electricityTariff = new ElectricityTariff();

    @Test
    public void testCalculatePrice_StandardCase1_CalculateAt50Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(50);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(90300, result);
    }

    @Test
    public void testCalculatePrice_StandardCase2_CalculateAt100Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(100);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(183600, result);
    }

    @Test
    public void testCalculatePrice_StandardCase3_CalculateAt200Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(200);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(400300, result);
    }

    @Test
    public void testCalculatePrice_StandardCase4_CalculateAt300Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(300);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(673200, result);
    }

    @Test
    public void testCalculatePrice_StandardCase5_CalculateAt400Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(400);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(978200, result);
    }

    @Test
    public void testCalculatePrice_StandardCase6_CalculateAt500Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(500);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(1293300, result);
    }

    @Test
    public void testCalculatePrice_ExceptionCase1_CalculateAt0Consumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(0);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(-1, result);
    }

    @Test
    public void testCalculatePrice_ExceptionCase2_CalculateAtNegativeConsumption() {
        List<AmountByStep> resultList = electricityTariff.calculatePrice(-1);
        double result;
        if(resultList.isEmpty()) {
            result = -1;
        } else {
            result = 0;
            for (AmountByStep amountByStep : resultList) {
                result += amountByStep.getAmount();
            }
        }
        assertEquals(-1, result);
    }
}