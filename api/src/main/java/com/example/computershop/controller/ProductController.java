package com.example.computershop.controller;

import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductControllerApi;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
