package com.example.computershop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductJoinedDto {
    String maker;
    String model;
    String type;
    Integer code;

}
