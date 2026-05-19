package com.example.computershop.mapper;

import com.example.computershop.model.entity.*;
import com.example.computershop.model.entity.OrdersEntity;
import com.example.specs.generated.model.OrdersDto;
import org.springframework.stereotype.Component;

@Component
public class OrdersMapper {
    public OrdersDto toOrdersDto (OrdersEntity ordersEntity) {
        return new OrdersDto()
                .orderId(ordersEntity.getCart().getCartId())
                .amount(ordersEntity.getAmount())
                .status(ordersEntity.getStatus());
    }

//    public OrdersEntity toOrdersEntity(OrdersDto ordersDto, ProductEntity foundProductEntity, UsersEntity foundUsersEntity, CartEntity foundCartEntity) {
//        OrdersEntity ordersEntity = new OrdersEntity();
//        ordersEntity.setCart(foundCartEntity);
//        ordersEntity.setAmount(ordersDto.getAmount());
//        ordersEntity.setStatus(ordersDto.getStatus());
//        return ordersEntity;
//    }
    public OrdersEntity toOrdersEntity(OrdersDto ordersDto, CartEntity foundCartEntity) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setCart(foundCartEntity);
        ordersEntity.setAmount(ordersDto.getAmount());
        ordersEntity.setStatus(ordersDto.getStatus());
        return ordersEntity;
    }
}
