package com.example.computershop.controller;

import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductControllerApi;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import jakarta.validation.Valid;
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
@RequestMapping("/admin/products")
public class ProductController implements ProductControllerApi {

    private final ProductService productService;
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<com.example.specs.generated.model.ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    @GetMapping
    public ResponseEntity<ProductDto> getProduct(@RequestParam String model) {
         return ResponseEntity.ok(productService.getProduct(model));
    }

    @GetMapping("/fullproduct")
    public ResponseEntity<List<ProductJoinedDto>> getJoinedProducts() {
        return ResponseEntity.ok(productService.getAllProductsJoined());
    }

    @PostMapping()
    public ResponseEntity<ProductDto> insertIntoProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @PatchMapping("/{model}")
    public ResponseEntity<ProductDto> patchProductPartially(@PathVariable String model, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProductPartially(model, productDto));
    }

    @DeleteMapping("/{model}")
    public ResponseEntity<Void> deleteFromProduct(@PathVariable("model") String model) {
        productService.delete(model);

        return ResponseEntity.ok(null);
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
