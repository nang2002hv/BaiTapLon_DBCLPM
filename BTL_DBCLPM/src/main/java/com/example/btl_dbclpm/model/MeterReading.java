package com.example.btl_dbclpm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    private Double previousReading = 0.0;
    private Double currentReading = 0.0;
    private String status ="WAITING_FOR_INPUT";
    @ManyToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;
}
