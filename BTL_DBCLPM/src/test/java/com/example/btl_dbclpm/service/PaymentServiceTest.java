package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePayment_WithValidPayment_ReturnsSavedPayment() {
        // Given
        Payment payment = new Payment();
        payment.setAmount(1000.0);
        payment.setPaymentMethod("Cash");
        payment.setPaymentStatus("Pending");

        // When
        when(paymentRepository.save(payment)).thenReturn(payment);
        Payment savedPayment = paymentService.savePayment(payment);

        // Then
        assertEquals(payment, savedPayment);
        assertEquals("Vnpay", savedPayment.getPaymentMethod()); // Assuming PaymentService updates payment method
        assertEquals("ppay", savedPayment.getPaymentStatus()); // Assuming PaymentService updates payment status
        verify(paymentRepository, times(1)).save(payment);
    }
}
