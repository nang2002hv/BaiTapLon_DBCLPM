package com.example.btl_dbclpm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String billCode = "";
    private Date dateUpdate = null;
    private long consumption = -1;
    private long amountTax = -1;
    private long amountBeforeTax = -1;
    private final double taxRate = 0.08;
    private long amountAfterTax = -1;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meter_reading_id")
    private MeterReading reading;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill", orphanRemoval = true)
    private List<AmountByStep> amountByStep = new ArrayList<>();
}
