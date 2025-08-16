package com.example.computershop.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    @Valid
    @NotBlank(message = "maker is mandatory")
    @NotNull(message = "maker is mandatory")
    String maker;
    String model;
    String type;

    public ProductDto() {

    }
}
