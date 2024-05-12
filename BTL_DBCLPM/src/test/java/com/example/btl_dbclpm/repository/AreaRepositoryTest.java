package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AreaRepositoryTest {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveArea_StandardCase_SaveArea() {
        Employee employee = new Employee();
        employee.setFullName("Yến Trinh");
        employee.setAuthorization("employee");
        employee.setEmail("AntionetteGandy449@nowhere.com");
        employee.setPassword("zN2IdpA9wXB6FrqONOEL3g==");
        employee.setPhoneNumber("(461) 186-0904");
        employee.setUsername("nang2002");
        employee.setEmployeeCode("NV68326");
        employee.setPosition("manager");

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByEmployee_StandardCase1_ReturnListArea() {
        Employee employee = new Employee();
        employee.setFullName("Yến Trinh");
        employee.setAuthorization("employee");
        employee.setEmail("AntionetteGandy449@nowhere.com");
        employee.setPassword("zN2IdpA9wXB6FrqONOEL3g==");
        employee.setPhoneNumber("(461) 186-0904");
        employee.setUsername("nang2002");
        employee.setEmployeeCode("NV68326");
        employee.setPosition("manager");
        employeeRepository.save(employee);

        Area area1 = Area.builder()
                .id(1L)
                .city("Cần Thơ")
                .district("Ô Môn")
                .wardCommune("Lê Bình")
                .employee(employee)
                .build();
        areaRepository.save(area1);

        Area area2 = Area.builder()
                .id(2L)
                .city("Cần Thơ")
                .district("Ninh Kiều")
                .wardCommune("An Hòa")
                .employee(employee)
                .build();
        areaRepository.save(area2);

        List<Area> actualAreas = areaRepository.findByEmployee(employee);

        assertNotNull(actualAreas);
        assertEquals(2, actualAreas.size());
    }
}
