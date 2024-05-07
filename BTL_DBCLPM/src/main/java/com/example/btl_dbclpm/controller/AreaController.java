package com.example.btl_dbclpm.controller;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/areas")
@CrossOrigin
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @PostMapping("/filter")
    public ResponseEntity<List<Area>> filterByEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(areaService.filterAreaByEmployee(employee));
    }
}
