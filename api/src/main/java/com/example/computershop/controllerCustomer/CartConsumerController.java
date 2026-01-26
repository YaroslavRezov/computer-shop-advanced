package com.example.computershop.controllerCustomer;

import com.example.computershop.service.CartService;
import com.example.specs.generated.api.CartConsumerControllerApi;
import com.example.specs.generated.model.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartConsumerController implements CartConsumerControllerApi {
    private final CartService cartService;

    @Override
    public ResponseEntity<List<CartDto>> getCustomerAllCartItems() {
        return ResponseEntity.ok(cartService.getAll());
    }
    @GetMapping("/user/{username}")
    public List<CartDto> getCartForCustomer(@PathVariable String username) {
        return cartService.getCartForUser(username);
    }
    @DeleteMapping("/user/{username}")
    public void clearCustomerCart(@PathVariable String username) {
        cartService.delete(username);
    }

    @Override
    public ResponseEntity<Void> deleteCustomerOneFromCart(@PathVariable("orderId") Long orderId) {
        cartService.delete(orderId);

        return ResponseEntity.noContent().build();
    }

}
