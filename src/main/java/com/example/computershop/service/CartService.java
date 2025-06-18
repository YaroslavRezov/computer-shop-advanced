package com.example.computershop.service;

import com.example.computershop.model.dto.CartDto;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public List<CartDto> getAll() {
        Iterable<CartEntity> cartEntities = cartRepository.findAll();
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(new CartDto(cartEntity.getOrderId(), cartEntity.getModel(), cartEntity.getCode(), cartEntity.getPrice(), cartEntity.getUsername()));
        }

        return cartDtoList;
    }

    public List<CartDto> getCartForUser(String username){
        Iterable<CartEntity> cartEntities = cartRepository.findByUsername(username);
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(new CartDto(cartEntity.getOrderId(), cartEntity.getModel(), cartEntity.getCode(), cartEntity.getPrice(), cartEntity.getUsername()));
        }

        return cartDtoList;
    }

    public CartDto save(CartDto requestCartDto) {
        CartEntity sourceCartEntity = new CartEntity();
        int price = productService.getPriceByCode(requestCartDto.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + requestCartDto.getCode()));


        sourceCartEntity.setModel(requestCartDto.getModel());
        sourceCartEntity.setCode(requestCartDto.getCode());
        sourceCartEntity.setPrice(price);
        sourceCartEntity.setUsername(requestCartDto.getUsername());



        CartEntity savedCartEntity = cartRepository.save(sourceCartEntity);

        CartDto responseCartDto = new CartDto();
        responseCartDto.setModel(savedCartEntity.getModel());
        responseCartDto.setCode(savedCartEntity.getCode());
        responseCartDto.setPrice(savedCartEntity.getPrice());
        responseCartDto.setUsername(savedCartEntity.getUsername());
        responseCartDto.setOrderId(savedCartEntity.getOrderId());
        return responseCartDto;
    }

    public void delete(String username){
        cartRepository.deleteByUsername(username);

    }

    public void delete(Long orderId){
        cartRepository.deleteById(orderId);

    }
}
