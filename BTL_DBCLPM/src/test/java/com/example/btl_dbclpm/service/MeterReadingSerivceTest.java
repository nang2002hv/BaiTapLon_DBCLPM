package com.example.btl_dbclpm.service;


import com.example.btl_dbclpm.enumU.StatusEnum;
import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterReadingRepository;
import com.example.btl_dbclpm.repository.MeterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterReadingSerivceTest {

    @Mock
    private MeterReadingRepository meterReadingRepository;

    @Mock
    private BillRepository billRepository;

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterReadingService meterReadingService;

    @Test
    public void testUpdateMeterReading() {
        // Tạo một đối tượng MeterReading
        MeterReading meterReading = new MeterReading();
        meterReading.setId(0L);
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(50);
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setMeterCode("47828680");
        when(meterReadingRepository.save(meterReading)).thenReturn(meterReading);
        when(meterRepository.findByMeterReadingsContains(meterReading)).thenReturn(meter);
        MeterReading returnedMeterReading = meterReadingService.updateMeterReading(meterReading);
        assertEquals(meterReading, returnedMeterReading);
    }

    @Test
    public void testUpdateMeterReading2() {
        // Tạo một đối tượng MeterReading
        MeterReading meterReading = new MeterReading();
        meterReading.setId(0L);
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(-50);
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setMeterCode("47828680");
        MeterReading returnedMeterReading = meterReadingService.updateMeterReading(meterReading);

        assertNull(returnedMeterReading);
    }

    @Test
    public void testCheckValidate() {
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(50);

        boolean result = meterReadingService.checkValidate(meterReading);

        assertTrue(result);
    }

    @Test
    public void testIsValidInput() {
        String input = "1234567890123456789124124124124";
        String result = meterReadingService.isValidInput(input);

        assertEquals("Không được nhập số lớn hơn 17 chữ số", result);
    }

    @Test
    public void testIsValidInput1() {
        String input = "-12";
        String result = meterReadingService.isValidInput(input);

        assertEquals("Không được nhập số âm", result);
    }

    @Test
    public void testIsValidInput2() {
        String input = "abc";
        String result = meterReadingService.isValidInput(input);

        assertEquals("Không nhập chữ cái hoặc kí tự đặc biệt", result);
    }
    @Test
    public void testIsValidInput3() {
        String input = "12";
        String result = meterReadingService.isValidInput(input);

        assertEquals("pass", result);
    }

    @Test
    public void testFilterByArea() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] mysqlFormattedDate = currentDate.format(formatter).split("-");
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        List<Meter> meterList = meterRepository.findByArea(area); //id
        List<MeterReading> meterReadings = new ArrayList<>();
        Meter meter = new Meter();
        meter.setTimeUpdate(java.sql.Date.valueOf("2024-05-01"));
        MeterReading meterReading = new MeterReading();
        meterReading.setId(0L);
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(50);
        meter.setMeterReadings(List.of(meterReading));
        meterList.add(meter);
        Meter meter1 = new Meter();
        meter1.setTimeUpdate(java.sql.Date.valueOf("2024-03-01"));
        MeterReading meterReading1 = new MeterReading();
        meterReading1.setId(1L);
        meterReading1.setCurrentReading(100);
        meterReading1.setPreviousReading(50);
        meter1.setMeterReadings(List.of(meterReading1));
        meterList.add(meter1);
        Meter meter2 = new Meter();
        meter2.setTimeUpdate(java.sql.Date.valueOf("2024-05-01"));
        meterList.add(meter2);

        when(meterRepository.findByArea(area)).thenReturn(meterList);


        List<MeterReading> meters1 = meterReadingService.filterByArea(area);
        assertEquals(50, meters1.get(0).getPreviousReading());
        assertEquals(100, meters1.get(1).getPreviousReading());
        assertEquals(0, meters1.get(2).getCurrentReading());

    }
    @Test
    public void testFilterByArea2() {

        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");

        List<Meter> meterList = new ArrayList<>();
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setMeterCode("47828680");
        meter.setArea(area);
        meterList.add(meter);

        MeterReading meterReading = new MeterReading();
        meterReading.setId(1L);
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(50);
        meterReading.setMeter(meter);

        when(meterRepository.findByArea(area)).thenReturn(null);
        assertNull(meterReadingService.filterByArea(area));
    }

}
