package com.example.computershop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaptopDto {
    private Integer code;
    private String model;
    private int speed;
    private int ram;
    private Double hd;
    private int price;
    private int screen;

    public LaptopDto() {

    }
}
