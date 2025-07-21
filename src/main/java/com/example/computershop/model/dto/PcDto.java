package com.example.computershop.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PcDto {
    @Valid

    private Long code;
    private String model;

    @NotNull(message =  "speed is mandatory")
    private int speed;
    @NotNull(message =  "ram is mandatory")
    private int ram;
    @NotNull(message =  "hd is mandatory")
    private Double hd;
    @NotBlank(message = "cd is mandatory")
    @NotNull(message = "cd is mandatory")
    private String cd;
    @NotNull(message = "price is mandatory")
    private int price;

    public PcDto() {

    }
}
