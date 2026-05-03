package com.example.computershop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Table(name = "orders")
@Data
@Entity
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private CartEntity cart;
    @Column
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private UsersEntity usersEmail;
    @NotNull
    @Column
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = false)
    private ProductEntity product;
    @Column
    private Long code;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;
    @NotNull
    @Column
    private String status;
}
