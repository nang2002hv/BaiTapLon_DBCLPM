package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.AmountByStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmountByStepRepository extends JpaRepository<AmountByStep, Long> {
    List<AmountByStep> findByBillIdAndStep(long billId, long step);
}
