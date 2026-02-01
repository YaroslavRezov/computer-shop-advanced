package com.example.computershop.mapper;

import com.example.computershop.model.entity.CartEntity;
import com.example.specs.generated.model.CartDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CartMapper {

    public List<CartDto> toCartDtoList(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(this::toCartDto)
                .toList();
    }

    public CartDto toCartDto (CartEntity cartEntity) {
        return new CartDto()
                .model(cartEntity.getProduct().getModel())
                .code(cartEntity.getCode())
                .price(cartEntity.getPrice())
                .username(cartEntity.getUser().getUsername())
                .orderId(cartEntity.getOrderId())
                .type(cartEntity.getProduct().getType());
    }
}
