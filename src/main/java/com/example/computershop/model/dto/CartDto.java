package com.example.computershop.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class CartDto {
    private Long orderId;
    private String model;
    private Long code;
    private int price;
    private String userId;
}
