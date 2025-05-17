package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_orderId_seq")
    @SequenceGenerator(name = "cart_orderId_seq", sequenceName = "cart_orderId_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long orderId;
    private String model;
    private Long code;
    private int price;
    private String userId;


}
