package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testPay() throws Exception {
        Customer customer = new Customer();
        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentService.getCustomerPayment(any(Customer.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments/get-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(payment.getId()));
    }

    @Test
    public void testSavePayment() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentService.savePayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"amount\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreatePayment() throws Exception {
        String paymentUrl = "http://example.com/payment";
        when(paymentService.generatePaymentUrl(anyDouble(), anyString(), anyString(), anyString(), anyLong())).thenReturn(paymentUrl);

        mockMvc.perform(get("/api/payments/create-payment")
                        .param("amount", "100.0")
                        .param("bankCode", "VCB")
                        .param("orderInfo", "Order 123")
                        .param("language", "en")
                        .param("paymentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(paymentUrl));
    }

    @Test
    public void testPaymentSuccess() throws Exception {
        Map<String, String> result = new HashMap<>();
        result.put("transactionRef", "12345");
        result.put("responseCode", "00");
        result.put("transactionNo", "67890");
        result.put("payDate", "2022-01-01");

        mockMvc.perform(get("/api/payments/payment-success")
                        .param("transactionRef", "12345")
                        .param("responseCode", "00")
                        .param("transactionNo", "67890")
                        .param("payDate", "2022-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionRef").value("12345"))
                .andExpect(jsonPath("$.responseCode").value("00"))
                .andExpect(jsonPath("$.transactionNo").value("67890"))
                .andExpect(jsonPath("$.payDate").value("2022-01-01"));
    }
}
