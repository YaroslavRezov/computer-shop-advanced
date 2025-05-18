package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/cart")
public class CartConsumerController {
    private final CartService cartService;

    @GetMapping("/all")
    public List<CartDto> getAllCartItems() {
        return cartService.getAll();
    }
    @GetMapping("/user")
    public List<CartDto> getCartForCustomer() {
        return cartService.getCartForUser("customer");
    }
}
