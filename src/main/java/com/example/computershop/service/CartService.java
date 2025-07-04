package com.example.computershop.service;

import com.example.computershop.model.dto.CartDto;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.PcDto;
import com.example.computershop.model.entity.*;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.repository.CartRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.repository.UsersRepository;
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
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    public List<CartDto> getAll() {
        Iterable<CartEntity> cartEntities = cartRepository.findAll();
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(new CartDto(cartEntity.getOrderId(), cartEntity.getProduct().getModel(), cartEntity.getCode(), cartEntity.getProduct().getType(), cartEntity.getUser().getUsername(), cartEntity.getPrice()));
        }

        return cartDtoList;
    }

    public List<CartDto> getCartForUser(String username){
        Iterable<CartEntity> cartEntities = cartRepository.findByUser(usersRepository.findByUsername(username).orElse(null));
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(new CartDto(cartEntity.getOrderId(), cartEntity.getProduct().getModel(), cartEntity.getCode(), cartEntity.getProduct().getType() , cartEntity.getUser().getUsername(), cartEntity.getPrice()));
        }

        return cartDtoList;
    }

    public CartDto save(CartDto requestCartDto) {

        CartEntity cartEntity = toCartEntity(requestCartDto);

        CartEntity savedCartEntity = cartRepository.save(cartEntity);

        CartDto responseCartDto = toCartDto(savedCartEntity);

        return responseCartDto;
    }

    public void delete(String username){
        UsersEntity foundUsersEntity = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        cartRepository.deleteByUser(foundUsersEntity);

    }

    public void delete(Long orderId){
        cartRepository.deleteById(orderId);

    }

    private CartDto toCartDto (CartEntity cartEntity) {
        CartDto cartDto = new CartDto();
        cartDto.setModel(cartEntity.getProduct().getModel());
        cartDto.setCode(cartEntity.getCode());
        cartDto.setPrice(cartEntity.getPrice());
        cartDto.setUsername(cartEntity.getUser().getUsername());
        cartDto.setOrderId(cartEntity.getOrderId());

        return cartDto;
    }

    private CartEntity toCartEntity(CartDto cartDto) {
        CartEntity cartEntity = new CartEntity();
        int price = productService.getPriceByCode(cartDto.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + cartDto.getCode()));
        ProductEntity foundProductEntity = productRepository.findById(cartDto.getModel()).orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        UsersEntity foundUsersEntity = usersRepository.findByUsername(cartDto.getUsername()).orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        cartEntity.setProduct(foundProductEntity);
        cartEntity.setUser(foundUsersEntity);
        cartEntity.setCode(cartDto.getCode());
        cartEntity.setPrice(price);
        return cartEntity;
    }
}

