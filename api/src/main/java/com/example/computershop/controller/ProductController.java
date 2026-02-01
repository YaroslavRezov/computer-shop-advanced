package com.example.computershop.controller;

import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductControllerApi;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController implements ProductControllerApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    public ResponseEntity<ProductDto> getProduct(String model) {
         return ResponseEntity.ok(productService.getProduct(model));
    }

    @Override
    public ResponseEntity<List<ProductJoinedDto>> getJoinedProducts() {
        return ResponseEntity.ok(productService.getAllProductsJoined());
    }

    @Override
    public ResponseEntity<ProductDto> insertIntoProduct(ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @Override
    public ResponseEntity<ProductDto> patchProductPartially(String model, ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProductPartially(model, productDto));
    }
    @Override
    public ResponseEntity<Void> deleteFromProduct(String model) {
        productService.delete(model);
        return ResponseEntity.noContent().build();
    }
}
