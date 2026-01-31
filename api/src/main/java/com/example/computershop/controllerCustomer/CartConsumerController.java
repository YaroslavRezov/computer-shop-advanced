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

    @Override
    public ResponseEntity<List<CartDto>> getCustomerCartForCustomer(String username) {
        return ResponseEntity.ok(cartService.getCartForUser(username));
    }

    @Override
    public ResponseEntity<Void> clearCustomerCart(String username) {
        cartService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteCustomerOneFromCart(Long orderId) {
        cartService.delete(orderId);

        return ResponseEntity.noContent().build();
    }

}
