package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
}
