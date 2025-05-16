package com.example.computershop.modelCustomer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "pc")
@Data
@Entity
public class PcEntity extends BaseDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pc_code_seq")
    @SequenceGenerator(name = "pc_code_seq", sequenceName = "pc_code_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long code;
    @Column
    private int speed;
    @Column
    private int ram;
    @Column
    private Double hd;
    @Column
    private String cd;

}
