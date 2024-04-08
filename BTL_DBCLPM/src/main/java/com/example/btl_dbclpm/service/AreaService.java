package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AreaService {
    private final AreaRepository areaRepository;



    public List<Area> filterAreaByEmployee(Employee employee) {
        return areaRepository.findByEmployee(employee);
    }

}
