package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AreaRepositoryTest {
    @Autowired
    private AreaRepository areaRepository;

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

        List<Area> expectedAreas = new ArrayList<>();
        expectedAreas.add(area1);
        expectedAreas.add(area2);

        List<Area> actualAreas = areaRepository.findByEmployee(employee);

        assertEquals(2, actualAreas.size());
        assertEquals(expectedAreas, actualAreas);
    }

    @Test
    public void testFilterByEmployee_ExceptionCase_NoEmployeeFound_ReturnEmpty() {
        Employee employee = new Employee();
        employee.setId(51L);
        employee.setFullName("Yến Trinh");
        employee.setAuthorization("employee");
        employee.setEmail("AntionetteGandy449@nowhere.com");
        employee.setPassword("zN2IdpA9wXB6FrqONOEL3g==");
        employee.setPhoneNumber("(461) 186-0904");
        employee.setUsername("nang2002");
        employee.setEmployeeCode("NV68326");
        employee.setPosition("manager");

        List<Area> areas = areaRepository.findByEmployee(employee);

        assertEquals(0, areas.size());
    }
}
