package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.BillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BillControllerTest {
    @Mock
    public BillService billService;

    @InjectMocks
    public BillController billController;

    @Test
    public void testCalculateBill_StandardCase_ReturnBill() {
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(50.0);
        Bill bill = new Bill();
        bill.setReading(meterReading);
        Bill billAfterCalculate = new Bill();
        billAfterCalculate.setReading(meterReading);
        billAfterCalculate.setConsumption(50);
        billAfterCalculate.setAmountBeforeTax(90300);
        billAfterCalculate.setAmountTax(7224);
        billAfterCalculate.setAmountAfterTax(97524);

        Mockito.when(billService.calculateBill(bill)).thenReturn(billAfterCalculate);

        ResponseEntity<Bill> result = billController.calculateBill(bill);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(50, Objects.requireNonNull(result.getBody()).getConsumption());
        assertEquals(90300, Objects.requireNonNull(result.getBody()).getAmountBeforeTax());
        assertEquals(7224, Objects.requireNonNull(result.getBody()).getAmountTax());
        assertEquals(97524, Objects.requireNonNull(result.getBody()).getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_ExceptionCase_ReturnBadRequest() {
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(50.0);
        meterReading.setCurrentReading(0.0);
        Bill bill = new Bill();
        bill.setReading(meterReading);
        Mockito.when(billService.calculateBill(bill)).thenReturn(null);

        ResponseEntity<Bill> result = billController.calculateBill(bill);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetBillsByMeter_StandardCase_ReturnLatestBill() {
        Meter meter = new Meter();
        meter.setId(1L);

        MeterReading meterReading1 = new MeterReading();
        meterReading1.setMeter(meter);

        MeterReading meterReading2 = new MeterReading();
        meterReading2.setMeter(meter);

        Bill bill1 = new Bill();
        bill1.setReading(meterReading1);
        Bill bill2 = new Bill();
        bill2.setReading(meterReading2);

        Mockito.when(billService.getBillsByMeter(meter)).thenReturn(bill2);

        ResponseEntity<Bill> result = billController.getBillsByMeter(meter);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(bill2, result.getBody());
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase_ReturnBadRequest() {
        Meter meter = new Meter();
        meter.setId(1L);
        Mockito.when(billService.getBillsByMeter(meter)).thenReturn(null);

        ResponseEntity<Bill> result = billController.getBillsByMeter(meter);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testSaveBill_StandardCase_ReturnBillSaved() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(50.0);
        meterReading.setStatus("WAITING_FOR_CALCULATION");
        bill.setReading(meterReading);
        bill.setConsumption(50);
        bill.setAmountBeforeTax(90300);
        bill.setAmountTax(7224);
        bill.setAmountAfterTax(97524);

        Bill billAfterSave = new Bill();
        meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(50.0);
        meterReading.setStatus("WAITING_FOR_PAYMENT");
        billAfterSave.setReading(meterReading);
        billAfterSave.setConsumption(50);
        billAfterSave.setAmountBeforeTax(90300);
        billAfterSave.setAmountTax(7224);
        billAfterSave.setAmountAfterTax(97524);
        Mockito.when(billService.saveBill(bill)).thenReturn(billAfterSave);

        ResponseEntity<Bill> result = billController.saveBill(bill);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(billAfterSave, result.getBody());
    }

    @Test
    public void testSaveBill_ExceptionCase_ReturnBadRequest() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(-1);

        Mockito.when(billService.saveBill(bill)).thenReturn(null);

        ResponseEntity<Bill> result = billController.saveBill(bill);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
