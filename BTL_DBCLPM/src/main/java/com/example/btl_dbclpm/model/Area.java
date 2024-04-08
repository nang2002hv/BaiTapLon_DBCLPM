package com.example.btl_dbclpm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name="area")
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wardCommune;
    private String district;
    private String city;
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Meter> meters;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;
}
