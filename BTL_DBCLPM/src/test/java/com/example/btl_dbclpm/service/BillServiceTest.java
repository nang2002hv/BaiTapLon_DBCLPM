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
    public void testGetBillsByMeter_StandardCase1_ReturnLatestBillOfMeter() {
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
    public void testGetBillsByMeter_ExceptionCase1_NoBillFound() {
        Meter meter = new Meter();
        meter.setId(1L);

        when(billRepository.findAll()).thenReturn(List.of());

        Bill returnedBill = billService.getBillsByMeter(meter);

        assertNull(returnedBill);
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase2_FoundBill_BillHasNoReading() {
        Meter meter = new Meter();
        meter.setId(1L);

        Bill bill = new Bill();
        bill.setReading(null);

        when(billRepository.findAll()).thenReturn(List.of(bill));

        Bill returnedBill = billService.getBillsByMeter(meter);

        assertNull(returnedBill);
    }

    @Test
    public void testGetBillsByMeter_ExceptionCase3_FoundBill_BillHasReading_MeterIdDoesNotMatch() {
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
    public void testCalculateBill_StandardCase1_CalculateAt50Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(50);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(50, result.getConsumption());
        assertEquals(90300, result.getAmountBeforeTax());
        assertEquals(7224, result.getAmountTax());
        assertEquals(97524, result.getAmountAfterTax());
        assertEquals(1, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase2_At100Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(100);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(100, result.getConsumption());
        assertEquals(183600, result.getAmountBeforeTax());
        assertEquals(14688, result.getAmountTax());
        assertEquals(198288, result.getAmountAfterTax());
        assertEquals(2, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
        assertEquals(2, result.getAmountByStep().get(1).getStep());
        assertEquals(93300, result.getAmountByStep().get(1).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase3_At200Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(200);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(200, result.getConsumption());
        assertEquals(400300, result.getAmountBeforeTax());
        assertEquals(32024, result.getAmountTax());
        assertEquals(432324, result.getAmountAfterTax());
        assertEquals(3, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
        assertEquals(2, result.getAmountByStep().get(1).getStep());
        assertEquals(93300, result.getAmountByStep().get(1).getAmount());
        assertEquals(3, result.getAmountByStep().get(2).getStep());
        assertEquals(216700, result.getAmountByStep().get(2).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase4_At300Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(300);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(300, result.getConsumption());
        assertEquals(673200, result.getAmountBeforeTax());
        assertEquals(53856, result.getAmountTax());
        assertEquals(727056, result.getAmountAfterTax());
        assertEquals(4, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
        assertEquals(2, result.getAmountByStep().get(1).getStep());
        assertEquals(93300, result.getAmountByStep().get(1).getAmount());
        assertEquals(3, result.getAmountByStep().get(2).getStep());
        assertEquals(216700, result.getAmountByStep().get(2).getAmount());
        assertEquals(4, result.getAmountByStep().get(3).getStep());
        assertEquals(272900, result.getAmountByStep().get(3).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase5_At400Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(400);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(400, result.getConsumption());
        assertEquals(978200, result.getAmountBeforeTax());
        assertEquals(78256, result.getAmountTax());
        assertEquals(1056456, result.getAmountAfterTax());
        assertEquals(5, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
        assertEquals(2, result.getAmountByStep().get(1).getStep());
        assertEquals(93300, result.getAmountByStep().get(1).getAmount());
        assertEquals(3, result.getAmountByStep().get(2).getStep());
        assertEquals(216700, result.getAmountByStep().get(2).getAmount());
        assertEquals(4, result.getAmountByStep().get(3).getStep());
        assertEquals(272900, result.getAmountByStep().get(3).getAmount());
        assertEquals(5, result.getAmountByStep().get(4).getStep());
        assertEquals(305000, result.getAmountByStep().get(4).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase6_At500Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(500);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(500, result.getConsumption());
        assertEquals(1293300, result.getAmountBeforeTax());
        assertEquals(103464, result.getAmountTax());
        assertEquals(1396764, result.getAmountAfterTax());
        assertEquals(6, result.getAmountByStep().size());
        assertEquals(1, result.getAmountByStep().get(0).getStep());
        assertEquals(90300, result.getAmountByStep().get(0).getAmount());
        assertEquals(2, result.getAmountByStep().get(1).getStep());
        assertEquals(93300, result.getAmountByStep().get(1).getAmount());
        assertEquals(3, result.getAmountByStep().get(2).getStep());
        assertEquals(216700, result.getAmountByStep().get(2).getAmount());
        assertEquals(4, result.getAmountByStep().get(3).getStep());
        assertEquals(272900, result.getAmountByStep().get(3).getAmount());
        assertEquals(5, result.getAmountByStep().get(4).getStep());
        assertEquals(305000, result.getAmountByStep().get(4).getAmount());
        assertEquals(6, result.getAmountByStep().get(5).getStep());
        assertEquals(315100, result.getAmountByStep().get(5).getAmount());
    }

    @Test
    public void testCalculateBill_StandardCase7_At0Consumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(0);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertEquals(0, result.getConsumption());
        assertEquals(0, result.getAmountBeforeTax());
        assertEquals(0, result.getAmountTax());
        assertEquals(0, result.getAmountAfterTax());
        assertEquals(0, result.getAmountByStep().size());
    }

    @Test
    public void testCalculateBill_ExceptionCase1_CalculateAtNegativeConsumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(10);
        meterReading.setCurrentReading(1);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertNull(result);
    }

    @Test
    public void testCalculateBill_ExceptionCase2_CalculateAtNegativePreviousReading() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(-1);
        meterReading.setCurrentReading(10);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertNull(result);
    }

    @Test
    public void testCalculateBill_ExceptionCase3_CalculateAtNegativeCurrentReading() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(10);
        meterReading.setCurrentReading(-1);
        bill.setReading(meterReading);
        Bill result = billService.calculateBill(bill);
        assertNull(result);
    }

    @Test
    public void testSaveBill_StandardCase1_ReturnBillSaved() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(50);
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
    public void testSaveBill_ExceptionCase1_NegativeConsumption() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(-100);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase2_NegativeAmountBeforeTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase3_NegativeAmountTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase4_NegativeAmountAfterTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(10);
        bill.setAmountAfterTax(-1);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }

    @Test
    public void testSaveBill_ExceptionCase5_AmountBeforeTaxNotEqualSumOfAmountBeforeTaxAndAmountTax() {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(100);
        bill.setAmountTax(10);
        bill.setAmountAfterTax(120);

        Bill result = billService.saveBill(bill);

        assertNull(result);
    }
}
