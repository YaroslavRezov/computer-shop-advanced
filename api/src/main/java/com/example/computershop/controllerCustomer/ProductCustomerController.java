package com.example.computershop.controllerCustomer;

import com.example.computershop.service.CartService;
import com.example.computershop.service.ProductService;
import com.example.specs.generated.api.ProductCustomerControllerApi;
import com.example.specs.generated.model.CartDto;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductCustomerController implements ProductCustomerControllerApi{

    private final ProductService productService;
    private final CartService cartService;

    @Override
    public ResponseEntity<List<ProductDto>> getCustomerProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    public ResponseEntity<ProductDto> getCustomerProduct(String model) {
        return ResponseEntity.ok(productService.getProduct(model));
    }

    @Override
    public ResponseEntity<List<ProductJoinedDto>> getCustomerJoinedProducts() {
        return ResponseEntity.ok(productService.getAllProductsJoined());
    }
    @Override
    public ResponseEntity<CartDto> insertCustomerProductIntoCart(CartDto cartDto) {
        return ResponseEntity.ok(cartService.save(cartDto));
    }
}
