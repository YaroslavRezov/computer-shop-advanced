package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "pc")
@Data
@Entity
public class PcEntity extends BaseDeviceEntity {

    @Column
    private int speed;
    @Column
    private int ram;
    @Column
    private Double hd;
    @Column
    private String cd;

}
