package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
    boolean existsByUsername(String username);
}
