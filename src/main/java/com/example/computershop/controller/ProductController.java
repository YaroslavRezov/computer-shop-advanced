package com.example.computershop.controller;

import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    ProductDto insertIntoProduct(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }
    @DeleteMapping("/{model}")
    void deleteFromProduct(@PathVariable("model") String model) {
        productService.delete(model);

    }
}
