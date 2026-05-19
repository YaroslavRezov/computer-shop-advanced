package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
@Table(name = "cart_device_product")
@Data
@Entity
public class CartDeviceProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_device_product_id_seq")
    @SequenceGenerator(name = "cart_device_product_id_seq", sequenceName = "cart_device_product_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id", nullable = false)
    private CartEntity cartId;
    @Column(name = "code")
    private Long code;
    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = false)
    private ProductEntity productEntity;
}
