package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.AreaService;
import com.example.btl_dbclpm.service.BillService;
import com.example.btl_dbclpm.service.MeterReadingService;
import com.example.btl_dbclpm.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/meter-reading")
@CrossOrigin
@RequiredArgsConstructor
public class MeterReadingController {
    private final MeterReadingService meterReadingService;

    @PostMapping("/save")
    public ResponseEntity<MeterReading> saveMeterReading(@RequestBody MeterReading meterReading) {
        MeterReading meterReading1 = meterReadingService.updateMeterReading(meterReading);
        if(meterReading1 != null) {
            return ResponseEntity.ok(meterReading1);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/filter")
    public List<MeterReading> filterByArea(@RequestBody Area area) {
        return meterReadingService.filterByArea(area);
    }
}
