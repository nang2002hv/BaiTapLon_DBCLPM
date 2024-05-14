package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.AreaService;
import com.example.btl_dbclpm.service.BillService;
import com.example.btl_dbclpm.service.MeterReadingService;
import com.example.btl_dbclpm.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<MeterReading>> filterByArea(@RequestBody Area area) {
        List<MeterReading> meterReading = meterReadingService.filterByArea(area);
        return meterReading != null ? ResponseEntity.ok(meterReading) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateInput(@RequestBody String value) {
        String validationResult = meterReadingService.isValidInput(value.trim());
        if(validationResult.equals("pass")) {
            return ResponseEntity.ok("pass");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
        }
    }
}
