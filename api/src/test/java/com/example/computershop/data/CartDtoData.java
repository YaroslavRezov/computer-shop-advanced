package com.example.computershop.data;

import com.example.specs.generated.model.CartDto;

public class CartDtoData {

    public static CartDto createCartDto1() {
        return new CartDto()
                .model("1276")
                .code(1L)
                .price(600)
                .username("Omen")
                .orderId(103L)
                .type("PC");
    }


    public static CartDto createCartDto2() {
        return new CartDto()
                .model("b276a11d-c526-4f74-b3c7-95ff94bf7147")
                .code(31L)
                .price(111111)
                .username("Omen")
                .orderId(104L)
                .type("PC");
    }
}
