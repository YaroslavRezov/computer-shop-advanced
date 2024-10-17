package com.example.computershop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    String maker;
    String model;
    String type;

    public ProductDto() {

    }
}
