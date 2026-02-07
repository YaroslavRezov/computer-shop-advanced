package com.example.computershop.data;

import com.example.specs.generated.model.LaptopDto;

public class LaptopDtoData {

    public static LaptopDto createLaptopDto1() {
        return new LaptopDto()
                .model("1298")
                .speed(350)
                .ram(32)
                .hd(4.0)
                .price(700)
                .screen(11)
                .code(1L);
    }

    public static LaptopDto createLaptopDto2() {
        return new LaptopDto()
                .model("74af7054-64f5-46b3-8a98-a0e16e0f9554")
                .speed(1122)
                .ram(1122)
                .hd(11.0)
                .price(112211)
                .screen(11)
                .code(8L);
    }
}
