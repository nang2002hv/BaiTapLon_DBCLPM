package com.example.btl_dbclpm.tariff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectricityTariffTest {
    private final ElectricityTariff electricityTariff = new ElectricityTariff();

    @Test
    public void testCalculatePrice_StandardCase1_CalculateAt50Consumption() {
        double result = electricityTariff.calculatePrice(50);
        assertEquals(90300, result);
    }

    @Test
    public void testCalculatePrice_StandardCase2_CalculateAt100Consumption() {
        double result = electricityTariff.calculatePrice(100);
        assertEquals(183600, result);
    }

    @Test
    public void testCalculatePrice_StandardCase3_CalculateAt200Consumption() {
        double result = electricityTariff.calculatePrice(200);
        assertEquals(400300, result);
    }

    @Test
    public void testCalculatePrice_StandardCase4_CalculateAt300Consumption() {
        double result = electricityTariff.calculatePrice(300);
        assertEquals(673200, result);
    }

    @Test
    public void testCalculatePrice_StandardCase5_CalculateAt400Consumption() {
        double result = electricityTariff.calculatePrice(400);
        assertEquals(978200, result);
    }

    @Test
    public void testCalculatePrice_StandardCase6_CalculateAt500Consumption() {
        double result = electricityTariff.calculatePrice(500);
        assertEquals(1293300, result);
    }

    @Test
    public void testCalculatePrice_StandardCase7_CalculateAt0Consumption() {
        double result = electricityTariff.calculatePrice(0);
        assertEquals(0, result);
    }

    @Test
    public void testCalculatePrice_ExceptionCase8_CalculateAtNegativeConsumption() {
        double result = electricityTariff.calculatePrice(-1);
        assertEquals(-1, result);
    }
}