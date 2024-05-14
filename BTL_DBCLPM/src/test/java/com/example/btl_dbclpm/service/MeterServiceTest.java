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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterServiceTest {
    @InjectMocks
    private MeterService MeterService;

    @Mock
    private MeterRepository meterRepository;

//    @Test
//    public void testGetBillsByMeter_StandardCase1_ReturnLatestBillOfMeter() {
//        Area area = new Area();
//        area.setId(1L);
//        area.setCity("Cần Thơ");
//        area.setDistrict("Ô Môn");
//        area.setWardCommune("Lê Bình");
//
//        List<Meter> meterList = Arrays.asList(
//                Meter.builder()
//                        .id(1L)
//                        .meterCode("47828680")
//                        .area(area)
//                        .build(),
//                Meter.builder()
//                        .id(2L)
//                        .meterCode("30464938")
//                        .area(area)
//                        .build()
//        );
//
//        when(meterRepository.findByArea(area)).thenReturn(Arrays.asList(bill1, bill2));
//
//        Bill returnedBill = billService.getBillsByMeter(meter);
//
//        assertEquals(bill2, returnedBill);
//    }
}
