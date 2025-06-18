package com.example.computershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@Data
public class CartDto {
    private Long orderId;
    private String model;
    private Long code;
    private int price;
    private String username;
    public CartDto() {

    }
}
