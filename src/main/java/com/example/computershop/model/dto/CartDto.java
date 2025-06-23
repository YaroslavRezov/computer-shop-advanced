package com.example.computershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartDto {
    private Long orderId;
    private String model;
    private Long code;
    private String type;
    private String username;
    private int price;

}
