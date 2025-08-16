package com.example.computershop.controller;

import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping
    ProductDto getProduct(@RequestParam String model) {
        return productService.getProduct(model);
    }

    @GetMapping("/fullproduct")
    List<ProductJoinedDto> getJoinedProducts() {
        return productService.getAllProductsJoined();
    }

    @PostMapping()
    ProductDto insertIntoProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PatchMapping("/{model}")
    public ProductDto patchProductPartially(@PathVariable String model, @Valid @RequestBody ProductDto productDto) {
        return productService.updateProductPartially(model, productDto);
    }

    @DeleteMapping("/{model}")
    void deleteFromProduct(@PathVariable("model") String model) {
        productService.delete(model);

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
