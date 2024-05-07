package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading,Long> {
}
