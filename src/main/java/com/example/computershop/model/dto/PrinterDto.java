package com.example.computershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrinterDto {
    private Long code;
    private String model;
    private String color;
    private String type;
    private Integer price;

    public PrinterDto() {

    }
}
