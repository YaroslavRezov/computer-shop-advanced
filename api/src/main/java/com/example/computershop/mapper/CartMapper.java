package com.example.computershop.mapper;

import com.example.computershop.model.entity.CartEntity;
import com.example.specs.generated.model.CartDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {
    public CartDto toCartDto (CartEntity cartEntity) {
        return new CartDto()
                .model(cartEntity.getProduct().getModel())
                .code(cartEntity.getCode())
                .price(cartEntity.getPrice())
                .username(cartEntity.getUser().getUsername())
                .orderId(cartEntity.getOrderId());
    }

    public CartDto toCartDtoAndGet(CartEntity cartEntity) {
        return new CartDto()
                .orderId(cartEntity.getOrderId())
                .model(cartEntity.getProduct().getModel())
                .code(cartEntity.getCode())
                .type(cartEntity.getProduct().getType())
                .username(cartEntity.getUser().getUsername())
                .price(cartEntity.getPrice());

    }

    public List<CartDto> toCartDtoList(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(cartEntity -> toCartDtoAndGet(cartEntity))
                .toList();
    }
}
