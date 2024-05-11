package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.service.AreaService;
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
import org.springframework.test.web.servlet.ResultActions;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AreaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AreaControllerTest {

    @MockBean
    private AreaService areaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFilterByEmployee_StandardCase1_ReturnListArea() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("Yến Trinh");
        employee.setAuthorization("employee");
        employee.setEmail("AntionetteGandy449@nowhere.com");
        employee.setPassword("zN2IdpA9wXB6FrqONOEL3g==");
        employee.setPhoneNumber("(461) 186-0904");
        employee.setUsername("nang2002");
        employee.setEmployeeCode("NV68326");
        employee.setPosition("manager");
        Area area1 = Area.builder()
                .id(1L)
                .city("Cần Thơ")
                .district("Ô Môn")
                .wardCommune("Lê Bình")
                .employee(employee)
                .build();
        Area area2 = Area.builder()
                .id(2L)
                .city("Cần Thơ")
                .district("Ninh Kiều")
                .wardCommune("An Hòa")
                .employee(employee)
                .build();
        List<Area> expectedAreas = Arrays.asList(area1, area2);

        when(areaService.filterAreaByEmployee(employee)).thenReturn(expectedAreas);

        ResultActions resultActions = mockMvc.perform(post("/api/areas/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].city", is(area1.getCity())))
                .andExpect(jsonPath("$[1].city", is(area2.getCity())));
    }
}