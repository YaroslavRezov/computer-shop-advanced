package com.example.computershop.data;

import com.example.specs.generated.model.ProductJoinedDto;

public class ProductJoinedDtoData {

    public static ProductJoinedDto createProductJoinedDto1() {
        return new ProductJoinedDto()
                .model("1232")
                .maker("A")
                .type("PC")
                .code("1");
    }

    public static ProductJoinedDto createProductJoinedDto2() {
        return new ProductJoinedDto()
                .model("b276a11d-c526-4f74-b3c7-95ff94bf7147")
                .maker("bbbruu")
                .type("PC")
                .code("29");
    }
}
