package com.example.computershop.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaptopDto {
    @Valid

    private Long code;
    @NotBlank(message = "model is mandatory")
    @NotNull(message = "model is mandatory")
    private String model;
    @NotNull(message = "speed is mandatory")
    private int speed;
    @NotNull(message = "ram is mandatory")
    private int ram;
    @NotNull(message = "hd is mandatory")
    private Double hd;
    @NotNull(message = "price is mandatory")
    private int price;
    @NotNull(message = "screen is mandatory")
    private int screen;

    public LaptopDto() {

    }
}
