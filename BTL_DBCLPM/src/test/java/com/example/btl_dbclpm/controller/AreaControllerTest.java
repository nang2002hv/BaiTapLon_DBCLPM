package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.service.AreaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AreaControllerTest {
    @Mock
    private AreaService areaService;

    @InjectMocks
    private AreaController areaController;

    @Test
    public void testFilterByEmployee_StandardCase_ReturnListArea() {
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
                .id(50L)
                .city("Cần Thơ")
                .district("Ninh Kiều")
                .wardCommune("An Hòa")
                .employee(employee)
                .build();
        List<Area> expectedAreas = Arrays.asList(area1, area2);

        when(areaService.filterAreaByEmployee(employee)).thenReturn(expectedAreas);

        ResponseEntity<List<Area>> response = areaController.filterByEmployee(employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAreas, response.getBody());
    }

    @Test
    public void testFilterByEmployee_ExceptionCase_NoEmployeeFound_ReturnEmptyList() {
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

        ResponseEntity<List<Area>> response = areaController.filterByEmployee(employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }
}