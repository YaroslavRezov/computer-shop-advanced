package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductCustomerControllerApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/products")
public class ProductCustomerController {

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/all")

    List<ProductDto> getCustomerProducts() {
        return productService.getProducts();
    }

    @GetMapping
    ProductDto getCustomerProduct(@RequestParam String model) {
        return productService.getProduct(model);
    }

    @GetMapping("/fullproduct")
    List<ProductJoinedDto> getCustomerJoinedProducts() {
        return productService.getAllProductsJoined();
    }
    @PostMapping()
    CartDto insertCustomerIntoCart(@Valid @RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }
}
