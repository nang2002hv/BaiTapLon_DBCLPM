package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.BillService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BillController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BillControllerTest {
    @MockBean
    private BillService billService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCalculateBill_StandardCase1_ReturnBill() throws Exception {
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(50);
        Bill bill = new Bill();
        bill.setReading(meterReading);
        Bill billAfterCalculate = new Bill();
        billAfterCalculate.setReading(meterReading);
        billAfterCalculate.setConsumption(50);
        billAfterCalculate.setAmountBeforeTax(90300);
        billAfterCalculate.setAmountTax(7224);
        billAfterCalculate.setAmountAfterTax(97524);

        when(billService.calculateBill(bill)).thenReturn(billAfterCalculate);

        mockMvc.perform(post("/api/bills/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consumption", is(50)))
                .andExpect(jsonPath("$.amountBeforeTax", is(90300)))
                .andExpect(jsonPath("$.amountTax", is(7224)))
                .andExpect(jsonPath("$.amountAfterTax", is(97524)));
    }

    @Test
    public void testCalculateBill_StandardCase2_MeterReadingNotValid_ReturnBadRequest() throws Exception {
        MeterReading meterReading = new MeterReading();
        meterReading.setPreviousReading(50);
        meterReading.setCurrentReading(0);
        Bill bill = new Bill();
        bill.setReading(meterReading);
        when(billService.calculateBill(bill)).thenReturn(null);

        mockMvc.perform(post("/api/bills/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bill)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBillsByMeter_StandardCase1_ReturnLatestBill() throws Exception {
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

        when(billService.getBillsByMeter(meter)).thenReturn(bill2);

        MvcResult mvcResult = mockMvc.perform(post("/api/bills/get-bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(response);
        long id = responseJson.get("id").asLong();
        assertEquals(bill2.getId(), id);
    }

    @Test
    public void testGetBillsByMeter_StandardCase2_NoBillFound_ReturnBadRequest() throws Exception {
        Meter meter = new Meter();
        meter.setId(1L);
        when(billService.getBillsByMeter(meter)).thenReturn(null);

        mockMvc.perform(post("/api/bills/get-bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBill_StandardCase_ReturnBillSaved() throws Exception {
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

        Bill billAfterSave = new Bill();
        meterReading = new MeterReading();
        meterReading.setPreviousReading(0);
        meterReading.setCurrentReading(50);
        meterReading.setStatus("WAITING_FOR_PAYMENT");
        billAfterSave.setReading(meterReading);
        billAfterSave.setConsumption(50);
        billAfterSave.setAmountBeforeTax(90300);
        billAfterSave.setAmountTax(7224);
        billAfterSave.setAmountAfterTax(97524);
        when(billService.saveBill(bill)).thenReturn(billAfterSave);

        MvcResult mvcResult = mockMvc.perform(post("/api/bills/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bill)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(response);
        long consumption = responseJson.get("consumption").asLong();
        long amountBeforeTax = responseJson.get("amountBeforeTax").asLong();
        long amountTax = responseJson.get("amountTax").asLong();
        long amountAfterTax = responseJson.get("amountAfterTax").asLong();

        assertEquals(billAfterSave.getConsumption(), consumption);
        assertEquals(billAfterSave.getAmountBeforeTax(), amountBeforeTax);
        assertEquals(billAfterSave.getAmountTax(), amountTax);
        assertEquals(billAfterSave.getAmountAfterTax(), amountAfterTax);
    }

    @Test
    public void testSaveBill_StandardCase_SaveBillNotValid_ReturnBadRequest() throws Exception {
        Bill bill = new Bill();
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(0);
        meterReading.setPreviousReading(100);
        bill.setReading(meterReading);
        bill.setConsumption(100);
        bill.setAmountBeforeTax(-1);

        when(billService.saveBill(bill)).thenReturn(null);

        mockMvc.perform(post("/api/bills/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bill)))
                .andExpect(status().isBadRequest());
    }
}
