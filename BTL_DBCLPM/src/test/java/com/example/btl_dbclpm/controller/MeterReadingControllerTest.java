package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.enumU.StatusEnum;
import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.MeterReadingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MeterReadingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MeterReadingControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeterReadingService meterReadingService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetMeterReading() throws Exception {
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        List<MeterReading> meterList = new ArrayList<>();
        MeterReading meter1 = new MeterReading();
        meter1.setStatus(String.valueOf(StatusEnum.WAITING_FOR_CALCULATION));
        meterList.add(meter1);
        MeterReading meter2 = new MeterReading();
        meter2.setStatus(String.valueOf(StatusEnum.WAITING_FOR_CALCULATION));
        meterList.add(meter2);
        MeterReading meter3 = new MeterReading();
        meter3.setStatus(String.valueOf(StatusEnum.WAITING_FOR_CALCULATION));
        meterList.add(meter3); // Ensure that the third MeterReading is added to the list

        when(meterReadingService.filterByArea(area)).thenReturn(meterList);
        mockMvc.perform(post("/api/meter-reading/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(area)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("WAITING_FOR_CALCULATION")))
                .andExpect(jsonPath("$[1].status", is("WAITING_FOR_CALCULATION")))
                .andExpect(jsonPath("$[2].status", is("WAITING_FOR_CALCULATION")));
    }

    @Test
    public void testSaveMeterReading() throws Exception {
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(50);
        meterReading.setPreviousReading(0);
        MeterReading meterReadingAfterSave = new MeterReading();
        meterReadingAfterSave.setCurrentReading(51);
        meterReadingAfterSave.setPreviousReading(0);
        meterReadingAfterSave.setStatus(String.valueOf(StatusEnum.WAITING_FOR_CALCULATION));
        when(meterReadingService.updateMeterReading(meterReading)).thenReturn(meterReadingAfterSave);
        mockMvc.perform(post("/api/meter-reading/save") // Ensure that the correct endpoint is used
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meterReading)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentReading", is(51))); // This assertion should now pass
    }


}
