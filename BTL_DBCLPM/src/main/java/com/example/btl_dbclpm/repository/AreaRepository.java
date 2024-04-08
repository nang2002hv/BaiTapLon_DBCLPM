package com.example.btl_dbclpm.repository;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {


    public List<Area> findByEmployee(Employee employee);

    @Query("select id from Area where district = :district and wardCommune = :wardCommune and city = :city")
    public Integer findAreaIdByDistrictAndWardCommune(@Param("district") String district, @Param("wardCommune") String wardCommune,@Param("city") String city);


}
