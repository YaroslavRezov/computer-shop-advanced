package com.example.computershop.data;

import com.example.specs.generated.model.PcDto;

public class PcDtoData {

    public static PcDto createPcDto1() {
        return new PcDto()
                .model("1232")
                .speed(500)
                .ram(64)
                .hd(5.0)
                .cd("12x")
                .price(600)
                .code(1L);
    }

    public static PcDto createPcDto2() {
        return new PcDto()
                .model("b276a11d-c526-4f74-b3c7-95ff94bf7147")
                .speed(11111)
                .ram(11111)
                .hd(111.0)
                .cd("12")
                .price(11111111)
                .code(29L);
    }
}
