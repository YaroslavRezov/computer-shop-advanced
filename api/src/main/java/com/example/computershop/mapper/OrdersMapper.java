package com.example.computershop.mapper;

import com.example.computershop.model.entity.*;
import com.example.computershop.model.entity.OrdersEntity;
import com.example.specs.generated.model.OrdersDto;
import com.example.specs.generated.model.OrdersDto;
import org.springframework.stereotype.Component;

@Component
public class OrdersMapper {
    public OrdersDto toOrdersDto (OrdersEntity ordersEntity) {
        return new OrdersDto()
                .id(ordersEntity.getId())
                .orderId(ordersEntity.getCart().getOrderId())
                .amount(ordersEntity.getAmount())
                .email(ordersEntity.getUser().getEmail())
                .number(ordersEntity.getNumber())
                .model(ordersEntity.getProduct().getModel())
                .code(ordersEntity.getCode())
                .user(ordersEntity.getUser().getId())
                .status(ordersEntity.getStatus());
    }

    public OrdersEntity toOrdersEntity(OrdersDto ordersDto, ProductEntity foundProductEntity, UsersEntity foundUsersEntity, CartEntity foundCartEntity) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setCart(foundCartEntity);
        ordersEntity.setAmount(ordersDto.getAmount());
        ordersEntity.setUsersEmail(foundUsersEntity);
        ordersEntity.setNumber(ordersEntity.getNumber());
        ordersEntity.setProduct(foundProductEntity);
        ordersEntity.setCode(ordersDto.getCode());
        ordersEntity.setUser(foundUsersEntity);
        ordersEntity.setStatus(ordersDto.getStatus());
        return ordersEntity;
    }
}
