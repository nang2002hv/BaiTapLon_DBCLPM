package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public Optional<Employee> findById(Long id);
}
