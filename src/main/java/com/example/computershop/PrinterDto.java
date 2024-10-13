package com.example.computershop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrinterDto {
    private Integer code;
    private String model;
    private String color;
    private String type;
    private Integer price;
}
