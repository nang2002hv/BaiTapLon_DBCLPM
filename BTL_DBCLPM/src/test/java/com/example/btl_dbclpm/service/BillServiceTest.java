package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.*;
import com.example.btl_dbclpm.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {
    @InjectMocks
    private BillService billService;

    @Mock
    private BillRepository billRepository;

    @Test
    public void testGetBillsByMeter_StandardCase_ReturnLatestBillOfMeter() {
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

        when(billRepository.findAll()).thenReturn(Arrays.asList(bill1, bill2));

        Bill returnedBill = billService.getBillsByMeter(meter);

        assertEquals(bill2, returnedBill);
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase_NoBillFound() {
        Meter meter = new Meter();
        meter.setId(1L);

        when(billRepository.findAll()).thenReturn(List.of());

        Bill returnedBill = billService.getBillsByMeter(meter);

        assertNull(returnedBill);
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase_FoundBill_BillHasNoReading() {
        Meter meter = new Meter();
        meter.setId(1L);

        Bill bill = new Bill();
        bill.setReading(null);

        when(billRepository.findAll()).thenReturn(List.of(bill));

        Bill returnedBill = billService.getBillsByMeter(meter);

        assertNull(returnedBill);
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase_FoundBill_BillHasReading_MeterIdDoesNotMatch() {
        Meter meter1 = new Meter();
        meter1.setId(1L);

        Meter meter2 = new Meter();
        meter2.setId(2L);

        MeterReading meterReading = new MeterReading();
        meterReading.setMeter(meter2);

        Bill bill = new Bill();
        bill.setReading(meterReading);

        when(billRepository.findAll()).thenReturn(List.of(bill));

        Bill returnedBill = billService.getBillsByMeter(meter1);

        assertNull(returnedBill);
    }

    @Test
    public void testCalculateBill_StandardCase_CalculateAt50Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(50.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(50, result.getConsumption());
        assertEquals(90300, result.getAmountBeforeTax());
        assertEquals(7224, result.getAmountTax());
        assertEquals(97524, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At100Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(100.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(100, result.getConsumption());
        assertEquals(183600, result.getAmountBeforeTax());
        assertEquals(14688, result.getAmountTax());
        assertEquals(198288, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At200Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(200.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(200, result.getConsumption());
        assertEquals(400300, result.getAmountBeforeTax());
        assertEquals(32024, result.getAmountTax());
        assertEquals(432324, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At300Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(300.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(300, result.getConsumption());
        assertEquals(673200, result.getAmountBeforeTax());
        assertEquals(53856, result.getAmountTax());
        assertEquals(727056, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At400Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(400.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(400, result.getConsumption());
        assertEquals(978200, result.getAmountBeforeTax());
        assertEquals(78256, result.getAmountTax());
        assertEquals(1056456, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At500Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(500.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(500, result.getConsumption());
        assertEquals(1293300, result.getAmountBeforeTax());
        assertEquals(103464, result.getAmountTax());
        assertEquals(1396764, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_StandardCase_At0Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0.0);
        meterReading.setCurrentReading(0.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(0, result.getConsumption());
        assertEquals(0, result.getAmountBeforeTax());
        assertEquals(0, result.getAmountTax());
        assertEquals(0, result.getAmountAfterTax());
    }

    @Test
    public void testCalculateBill_ExceptionCase_CalculateAtNegativeConsumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(10.0);
        meterReading.setCurrentReading(1.0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertNull(result);
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

        Mockito.when(billRepository.save(bill)).thenReturn(bill);

        Bill result = billService.saveBill(bill);

        assertNotNull(result);
        assertEquals("WAITING_FOR_PAYMENT", result.getReading().getStatus());
        assertTrue(result.getBillCode().matches("^[a-fA-F0-9]{32}$"));
    }

    @Test
    public void testSaveBill_ExceptionCase_NegativeConsumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(-100);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase_NegativeAmountBeforeTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase_NegativeAmountTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase_NegativeAmountAfterTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(10);
        bill.setAmountAfterTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase_AmountBeforeTaxNotEqualSumOfAmountBeforeTaxAndAmountTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0.0);
        meterReading.setPreviousReading(100.0);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(10);
        bill.setAmountAfterTax(120);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }
}
