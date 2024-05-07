package com.example.btl_dbclpm.service;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.repository.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeterService {

    private final MeterRepository meterRepository;

    public List<Meter> filterByArea(Area area) {
        return meterRepository.findByArea(area);
    }

}
