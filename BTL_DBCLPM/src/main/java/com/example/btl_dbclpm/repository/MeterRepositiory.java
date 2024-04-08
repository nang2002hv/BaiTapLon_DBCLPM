package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterRepositiory extends JpaRepository<Meter,Long> {
    public Optional<Meter> findById(Long id);

    List<Meter> findByArea(Area area);

    public List<Meter> findAllByArea_Id(long id);

    public Meter findByMeterReadingsContains(MeterReading meterReading);

}
