package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Long> {
    List<Meter> findByArea(Area area);
    Meter findByMeterReadingsContains(MeterReading meterReading);
    Meter findByCustomer(Customer customer);
}
