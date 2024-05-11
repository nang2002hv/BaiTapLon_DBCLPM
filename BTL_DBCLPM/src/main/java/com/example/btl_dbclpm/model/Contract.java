package com.example.btl_dbclpm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contractCode;
    private String sellerRepresentation;
    private String customerCode;
    private String buyerRepresentation ;
    private String buyerEmail;
    private String buyerAddress;
    private String buyerPhoneNumber;
    private String meterInstallationLocation;
    private String taxCode;
}
