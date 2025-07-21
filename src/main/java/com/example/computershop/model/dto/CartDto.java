package com.example.computershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartDto {
    @Valid
    private Long orderId;
    @NotBlank(message = "model is mandatory")
    @NotNull(message =  "model is mandatory")
    private String model;

    private Long code;
    private String type;
    @NotBlank(message = "username is mandatory")
    @NotNull(message =  "username is mandatory")
    private String username;
    @NotNull(message =  "price is mandatory")
    private int price;

}
