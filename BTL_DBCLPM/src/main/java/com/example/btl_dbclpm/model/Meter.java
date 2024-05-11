package com.example.btl_dbclpm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String meterCode;
    private String installmentAddress;
    private Date installmentDate; //
    private String meterType;
    private Date timeUpdate; //
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private Area area;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MeterReading> meterReadings = new ArrayList<>();
}
