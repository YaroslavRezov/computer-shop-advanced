package com.example.computershop.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrinterDto {
    @Valid

    private Long code;

    private String model;
    @NotBlank(message = "color is mandatory")
    @NotNull(message =  "color is mandatory")
    private String color;
    @NotBlank(message = "type is mandatory")
    @NotNull(message =  "type is mandatory")
    private String type;
    @NotNull(message =  "price is mandatory")
    private Integer price;

    public PrinterDto() {

    }
}
