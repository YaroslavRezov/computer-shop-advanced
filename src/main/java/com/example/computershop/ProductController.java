package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    List<ProductDto> getProducts(){
        return productService.getProducts();
    }
    @GetMapping
    ProductDto getProduct(@RequestParam String model){
        return productService.getProduct(model);
    }
    @GetMapping("/fullproduct")
    List<ProductJoinedDto> getJoinedProducts(){
        return productService.getAllProductsJoined();
    }

}