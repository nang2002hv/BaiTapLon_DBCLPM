package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee")
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam Long id){
        MeterReading m = new MeterReading();
        Employee employee = employeeService.getEmployee(id);
        if(employee != null)
            return ResponseEntity.ok(employee); //200
        else
            return ResponseEntity.badRequest().build(); //404
    }
}
