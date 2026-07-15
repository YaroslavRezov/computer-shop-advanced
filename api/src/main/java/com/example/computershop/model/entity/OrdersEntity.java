package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "orders")
@Data
@Entity
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long orderId;
    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id", nullable = false)
    private CartEntity cartEntity;
    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "available";
        }
    }
}
