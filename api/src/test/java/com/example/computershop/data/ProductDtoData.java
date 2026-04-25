package com.example.computershop.data;

import com.example.specs.generated.model.ProductDto;
public class ProductDtoData {

    public static ProductDto createProductDto1() {
        return new ProductDto()
                .model("1232")
                .maker("A")
                .type("PC");
    }

    public static ProductDto createProductDto2() {
        return new ProductDto()
                .model("b276a11d-c526-4f74-b3c7-95ff94bf7147")
                .maker("bbbruu")
                .type("PC");
    }

    public static ProductDto createProductDto(String model, String maker, String type) {
        return new ProductDto()
                .model(model)
                .maker(maker)
                .type(type);
    }
}
