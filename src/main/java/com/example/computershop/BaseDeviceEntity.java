package com.example.computershop;

import jakarta.persistence.*;
import lombok.Data;
@MappedSuperclass
@Data
public abstract class BaseDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = true)
    private ProductEntity product;

    @Column
    private int price;

}
