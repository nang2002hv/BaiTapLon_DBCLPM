package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterServiceTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterService meterService;

    @Test
    public void testFilterByArea() {
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        List<Meter> meterList = new ArrayList<>();
        Meter meter = new Meter();
        meter.setMeterCode("47828680");
        meter.setArea(area);
        meterList.add(meter);
        when(meterRepository.findByArea(area)).thenReturn(meterList);
        List<Meter> returnedMeters = meterService.filterByArea(area);
        assertEquals(meterList, returnedMeters);
    }
}
