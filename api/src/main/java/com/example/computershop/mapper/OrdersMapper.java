package com.example.computershop.mapper;

import com.example.computershop.model.entity.OrdersEntity;
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
                .user(ordersEntity.getUser().getId());
    }
}
