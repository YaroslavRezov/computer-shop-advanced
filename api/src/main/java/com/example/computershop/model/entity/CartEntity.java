package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
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
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = true)
    private UsersEntity user;
    private Long code;
    private int price;



}
