package com.example.computershop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "laptop")
@Data
@Entity
public class LaptopEntity extends BaseDeviceEntity {

    @Column
    private int speed;
    @Column
    private int ram;
    @Column
    private Double hd;
    @Column
    private int screen;

}
