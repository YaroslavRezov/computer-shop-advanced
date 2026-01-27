package com.example.computershop.mapper;

import com.example.computershop.model.entity.CartEntity;
import com.example.specs.generated.model.CartDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapper {
    public CartDto toCartDto (CartEntity cartEntity) {
        CartDto cartDto = new CartDto();
        cartDto.setModel(cartEntity.getProduct().getModel());
        cartDto.setCode(cartEntity.getCode());
        cartDto.setPrice(cartEntity.getPrice());
        cartDto.setUsername(cartEntity.getUser().getUsername());
        cartDto.setOrderId(cartEntity.getOrderId());

        return cartDto;
    }

    public CartDto toCartDtoAndGet(CartEntity cartEntity) {
        CartDto cartDto = new CartDto();
        cartDto.setOrderId(cartDto.getOrderId());
        cartDto.setModel(cartEntity.getProduct().getModel());
        cartDto.setCode(cartEntity.getCode());
        cartDto.setType(cartEntity.getProduct().getType());
        cartDto.setUsername(cartEntity.getUser().getUsername());
        cartDto.setPrice(cartEntity.getPrice());
        return cartDto;
    }

    public List<CartDto> toCartDtoList(Iterable<CartEntity> cartEntities) {
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(toCartDtoAndGet(cartEntity));
        }
        return cartDtoList;
    }
}
