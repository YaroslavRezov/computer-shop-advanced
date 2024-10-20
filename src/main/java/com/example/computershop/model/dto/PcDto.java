package com.example.computershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PcDto {
    private Long code;
    private String model;
    private int speed;
    private int ram;
    private Double hd;
    private String cd;
    private int price;
}
