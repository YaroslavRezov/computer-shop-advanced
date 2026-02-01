package com.example.computershop.model.entity;

import com.example.computershop.model.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDeviceEntity {


    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "model", nullable = true)
    private ProductEntity product;

    @Column
    private int price;

}
