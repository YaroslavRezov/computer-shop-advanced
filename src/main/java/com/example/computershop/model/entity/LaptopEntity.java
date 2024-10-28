package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "laptop")
@Data
@Entity
public class LaptopEntity extends BaseDeviceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laptop_code_seq")
    @SequenceGenerator(name = "laptop_code_seq", sequenceName = "laptop_code_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long code;

    @Column
    private int speed;
    @Column
    private int ram;
    @Column
    private Double hd;
    @Column
    private int screen;


}
