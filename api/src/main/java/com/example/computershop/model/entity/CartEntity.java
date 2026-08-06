package com.example.computershop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name = "cart")
@Data
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_order_id_seq")
    @SequenceGenerator(name = "cart_order_id_seq", sequenceName = "cart_order_id_seq", allocationSize = 1)
    @Column(name = "cart_id", nullable = false, updatable = false)
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;
    @OneToMany(mappedBy = "cartEntity",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<CartDeviceProductEntity> devices = new ArrayList<>();

}
