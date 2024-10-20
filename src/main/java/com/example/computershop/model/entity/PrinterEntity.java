package com.example.computershop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "printer")
@Data
@Entity
public class PrinterEntity extends BaseDeviceEntity {


    @Column
    private String color;
    @Column
    private String type;;


}
