package com.example.computershop.modelCustomer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductJoinedDto {
    String maker;
    String model;
    String type;
    String code;
    //only for joined code is string

}
