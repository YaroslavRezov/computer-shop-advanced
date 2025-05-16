package com.example.computershop.modelCustomer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaptopDto {
    private Long code;
    private String model;
    private int speed;
    private int ram;
    private Double hd;
    private int price;
    private int screen;

    public LaptopDto() {

    }
}
