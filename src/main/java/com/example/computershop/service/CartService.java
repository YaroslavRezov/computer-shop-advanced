package com.example.computershop.service;

import com.example.computershop.model.dto.CartDto;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;
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

        CartEntity cartEntity = new CartEntity();
        int price = productService.getPriceByCode(requestCartDto.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + requestCartDto.getCode()));
        ProductEntity foundProductEntity = productRepository.findById(requestCartDto.getModel()).orElse(null);
        UsersEntity foundUsersEntity = usersRepository.findByUsername(requestCartDto.getUsername()).orElse(null);
        cartEntity.setProduct(foundProductEntity);
        cartEntity.setUser(foundUsersEntity);
        cartEntity.setCode(requestCartDto.getCode());
        cartEntity.setPrice(price);

        CartEntity savedCartEntity = cartRepository.save(cartEntity);



        CartDto responseCartDto = new CartDto();
        responseCartDto.setModel(savedCartEntity.getProduct().getModel());
        responseCartDto.setCode(savedCartEntity.getCode());
        responseCartDto.setPrice(savedCartEntity.getPrice());
        responseCartDto.setUsername(savedCartEntity.getUser().getUsername());
        responseCartDto.setOrderId(savedCartEntity.getOrderId());

        return responseCartDto;
    }

    public void delete(String username){
        UsersEntity foundUsersEntity = usersRepository.findByUsername(username).orElse(null);
        cartRepository.deleteByUser(foundUsersEntity);

    }

    public void delete(Long orderId){
        cartRepository.deleteById(orderId);

    }
}

