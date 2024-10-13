package com.example.computershop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PcDto {
    private Integer code;
    private String model;
    private int speed;
    private int ram;
    private Double hd;
    private String cd;
    private int price;
}
