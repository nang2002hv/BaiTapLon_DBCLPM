package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.service.BillService;
import com.example.btl_dbclpm.service.MeterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MeterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeterService meterService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetMeters() throws Exception {
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        List<Meter> meterList = new ArrayList<>();
        Meter meter1 = new Meter();
        meter1.setMeterCode("47828680");
        meter1.setArea(area);
        meterList.add(meter1);
        Meter meter2 = new Meter();
        meter2.setMeterCode("30464938");
        meter2.setArea(area);
        meterList.add(meter2);
        Meter meter3 = new Meter();
        meter3.setMeterCode("26725508");
        meter3.setArea(area);
        meterList.add(meter3);
        when(meterService.filterByArea(area)).thenReturn(meterList);
        mockMvc.perform(post("/api/meters/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(area)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meterCode", is("47828680")))
                .andExpect(jsonPath("$[1].meterCode", is("30464938")))
                .andExpect(jsonPath("$[2].meterCode", is("26725508")));
    }
}
