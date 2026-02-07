package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "cart")
@Data
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_order_id_seq")
    @SequenceGenerator(name = "cart_order_id_seq", sequenceName = "cart_order_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = true)
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private UsersEntity user;
    private Long code;
    private int price;

}
