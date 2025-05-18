package com.example.computershop.service;

import com.example.computershop.model.dto.CartDto;

import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartDto save(CartDto requestCartDto) {
        CartEntity sourceCartEntity = new CartEntity();
        int price = productService.getPriceByCode(requestCartDto.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + requestCartDto.getCode()));


        sourceCartEntity.setModel(requestCartDto.getModel());
        sourceCartEntity.setCode(requestCartDto.getCode());
        sourceCartEntity.setPrice(price);
        sourceCartEntity.setUserId(requestCartDto.getUserId());



        CartEntity savedCartEntity = cartRepository.save(sourceCartEntity);

        CartDto responseCartDto = new CartDto();
        responseCartDto.setModel(savedCartEntity.getModel());
        responseCartDto.setCode(savedCartEntity.getCode());
        responseCartDto.setPrice(savedCartEntity.getPrice());
        responseCartDto.setUserId(savedCartEntity.getUserId());
        responseCartDto.setOrderId(savedCartEntity.getOrderId());
        return responseCartDto;
    }
}
