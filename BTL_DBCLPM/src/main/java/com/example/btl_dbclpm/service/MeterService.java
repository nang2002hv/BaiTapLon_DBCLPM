package com.example.btl_dbclpm.service;


import com.example.btl_dbclpm.enumU.StatusEnum;
import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.repository.MeterRepositiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeterService {


    private final MeterRepositiory meterRepositiory;
    private final AreaService areaService;

    public List<Meter> filterByArea(Area area) {
        return meterRepositiory.findByArea(area);
    }

}
