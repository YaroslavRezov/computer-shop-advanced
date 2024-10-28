package com.example.computershop.model.entity;

import com.example.computershop.model.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
@MappedSuperclass
@Data
public abstract class BaseDeviceEntity {


    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = true)
    private ProductEntity product;

    @Column
    private int price;

}
