package com.example.computershop.modelCustomer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "product")
@Data
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String model;
    @Column
    private String maker;
    @Column
    private String type;


}

