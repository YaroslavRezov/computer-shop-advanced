package com.example.computershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductJoinedDto {
    String maker;
    String model;
    String type;
    Long code;

}
