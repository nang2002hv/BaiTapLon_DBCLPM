package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterRepository;
import com.example.btl_dbclpm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BillRepository billRepository;

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Customer customer;
    private Meter meter;
    private Bill bill;
    private Payment payment;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        meter = new Meter();
        meter.setId(1L);
        payment = new Payment();
        bill = new Bill();
        bill.setPayment(payment);

        lenient().when(meterRepository.findByCustomer(customer)).thenReturn(meter);
        lenient().when(billRepository.findAll()).thenReturn(Collections.singletonList(bill));
    }

    @Test
    void testUpdatePaymentStatusToPaid() {
        Long paymentId = 1L;
        when(paymentRepository.updatePaymentStatusToPaid(paymentId)).thenReturn(1);

        boolean result = paymentService.updatePaymentStatusToPaid(paymentId);

        assertTrue(result);
        verify(paymentRepository, times(1)).updatePaymentStatusToPaid(paymentId);
    }


    @Test
    void testGetCustomerPaymentWhenNoMeter() {
        when(meterRepository.findByCustomer(customer)).thenReturn(null);

        Payment result = paymentService.getCustomerPayment(customer);

        assertNull(result);
    }

    @Test
    void testGetCustomerPaymentWhenNoBill() {
        when(meterRepository.findByCustomer(customer)).thenReturn(meter);
        when(billRepository.findAll()).thenReturn(Collections.emptyList());

        Payment result = paymentService.getCustomerPayment(customer);

        assertNull(result);
    }

    @Test
    void testBuildQuery() throws UnsupportedEncodingException {
        Map<String, String> data = new HashMap<>();
        data.put("vnp_Version", "2.1.0");
        data.put("vnp_Command", "pay");

        String query = invokePrivateMethod(paymentService, "buildQuery", new Class[]{Map.class}, new Object[]{data});

        assertEquals("vnp_Command=pay&vnp_Version=2.1.0", query);
    }

    @Test
    void testHmacSHA512() {
        String key = "testkey";
        String data = "testdata";

        String hash = invokePrivateMethod(paymentService, "hmacSHA512", new Class[]{String.class, String.class}, new Object[]{key, data});

        assertNotNull(hash);
    }

    @Test
    void testGetRandomNumber() {
        String randomNum = invokePrivateMethod(paymentService, "getRandomNumber", new Class[]{int.class}, new Object[]{8});

        assertNotNull(randomNum);
        assertEquals(8, randomNum.length());
        assertTrue(randomNum.matches("\\d{8}"));
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivateMethod(Object instance, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        try {
            Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return (T) method.invoke(instance, parameters);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke private method", e);
        }
    }
}
