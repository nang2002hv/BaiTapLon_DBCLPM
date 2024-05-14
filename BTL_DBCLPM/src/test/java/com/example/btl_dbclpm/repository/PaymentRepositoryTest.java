package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSavePayment_StandardCase_SavePayment() {
        Payment payment = new Payment();
        payment.setAmount(1000.0);
        payment.setPaymentStatus("Chưa thanh toán");

        Payment savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment);
        assertThat(savedPayment.getId()).isGreaterThan(0);
        assertEquals("Chưa thanh toán", savedPayment.getPaymentStatus());
    }

    @Test
    @Transactional
    public void testUpdatePaymentStatusToPaid_StandardCase_UpdateStatus() {
        Payment payment = new Payment();
        payment.setAmount(1000.0);
        payment.setPaymentStatus("Chưa thanh toán");

        Payment savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment);
        assertThat(savedPayment.getId()).isGreaterThan(0);
        assertEquals("Chưa thanh toán", savedPayment.getPaymentStatus());

        int updatedCount = paymentRepository.updatePaymentStatusToPaid(savedPayment.getId());

        assertEquals(1, updatedCount);

        // Clear the persistence context to get the updated state
        entityManager.clear();

        // Refresh the entity to get the updated state
        Payment updatedPayment = paymentRepository.findById(savedPayment.getId()).orElse(null);

        assertNotNull(updatedPayment);
        assertEquals("Đã thanh toán", updatedPayment.getPaymentStatus());
    }

    @Test
    public void testFindAllPayments_StandardCase_ReturnListPayments() {
        Payment payment1 = new Payment();
        payment1.setAmount(1000.0);
        payment1.setPaymentStatus("Chưa thanh toán");

        Payment payment2 = new Payment();
        payment2.setAmount(2000.0);
        payment2.setPaymentStatus("Đã thanh toán");

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();

        assertNotNull(payments);
        assertEquals(2, payments.size());
        assertThat(payments).extracting(Payment::getPaymentStatus)
                .containsExactlyInAnyOrder("Chưa thanh toán", "Đã thanh toán");
    }
}
