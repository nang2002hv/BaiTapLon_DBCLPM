package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.paymentStatus = 'Đã thanh toán' WHERE p.id = :id")
    int updatePaymentStatusToPaid(Long id);
}
