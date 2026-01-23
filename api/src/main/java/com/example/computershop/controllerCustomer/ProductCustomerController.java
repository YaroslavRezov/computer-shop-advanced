package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductCustomerControllerApi;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/products")
public class ProductCustomerController implements ProductCustomerControllerApi{

    private final ProductService productService;
    private final CartService cartService;

    @Override
    @GetMapping("/all")

    public ResponseEntity<List<ProductDto>> getCustomerProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping
    public ResponseEntity<ProductDto> getCustomerProduct(@RequestParam String model) {
        return ResponseEntity.ok(productService.getProduct(model));
    }

    @GetMapping("/fullproduct")
    public ResponseEntity<List<ProductJoinedDto>> getCustomerJoinedProducts() {
        return ResponseEntity.ok(productService.getAllProductsJoined());
    }
    @PostMapping()
    public CartDto insertCustomerIntoCart(@Valid @RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }
}
