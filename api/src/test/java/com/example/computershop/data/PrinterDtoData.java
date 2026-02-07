package com.example.computershop.data;

import com.example.specs.generated.model.PrinterDto;

public class PrinterDtoData {

    public static PrinterDto createPrinterDto1() {
        return new PrinterDto()
                .model("1276")
                .color("Чернобелый")
                .type("Laser")
                .price(400)
                .code(1L);
    }

    public static PrinterDto createPrinterDto2() {
        return new PrinterDto()
                .model("88165743-dad3-486f-adf1-0abd207c2ef6")
                .color("Цветной")
                .type("lol")
                .price(7)
                .code(13L);
    }
}
