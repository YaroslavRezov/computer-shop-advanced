package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table(name = "cart")
@Data
@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_order_id_seq")
    @SequenceGenerator(name = "cart_order_id_seq", sequenceName = "cart_order_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long orderId;

    private String model;
    private Long code;
    private int price;
    private String username;


}
