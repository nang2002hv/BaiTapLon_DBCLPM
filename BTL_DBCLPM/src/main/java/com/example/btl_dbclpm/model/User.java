package com.example.btl_dbclpm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String fullName;
    protected String phoneNumber;
    protected String email;
    protected String password;
    @Column(name = "authorizations")
    protected String authorization;
    protected String username;
}
