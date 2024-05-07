package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meters")
@CrossOrigin
@RequiredArgsConstructor
public class MeterController {
    private final MeterService meterService;

    @PostMapping("/filter")
    public List<Meter> filterByArea(@RequestBody Area area) {
        return meterService.filterByArea(area);
    }

}
