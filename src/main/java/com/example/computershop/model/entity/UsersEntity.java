package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Data
@Entity
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    private String firstName;
    private String surname;
    private String userRole;
}
