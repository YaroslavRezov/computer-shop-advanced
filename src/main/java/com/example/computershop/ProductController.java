package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
}
