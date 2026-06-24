package com.example.computershop.mapper;

import com.example.computershop.model.entity.*;
import com.example.specs.generated.model.OrdersDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersMapper {

    public List<OrdersDto> toOrdersDtoList(List<OrdersView> ordersViews) {
        return ordersViews.stream()
                .map(this::toOrdersDto)
                .toList();
    }

    public OrdersDto toOrdersDto(OrdersView ordersView) {
        return new OrdersDto()
                .orderId(ordersView.getOrderId())
                .code(ordersView.getCode())
                .model(ordersView.getModel())
                .amount(ordersView.getAmount())
                .status(ordersView.getStatus())
                .username(ordersView.getUsername())
                .email(ordersView.getEmail());
    }


//    public OrdersDto toOrdersDto (OrdersEntity ordersEntity) {
//        return new OrdersDto()
//                .orderId(ordersEntity.getCartEntity().getCartId())
//                .amount(ordersEntity.getAmount())
//                .status(ordersEntity.getStatus());
//    }
//
//    public OrdersEntity toOrdersEntity(OrdersDto ordersDto, ProductEntity foundProductEntity, UsersEntity foundUsersEntity, CartEntity foundCartEntity) {
//        OrdersEntity ordersEntity = new OrdersEntity();
//        ordersEntity.setCart(foundCartEntity);
//        ordersEntity.setAmount(ordersDto.getAmount());
//        ordersEntity.setStatus(ordersDto.getStatus());
//        return ordersEntity;
//    }
//    public OrdersEntity toOrdersEntity(OrdersDto ordersDto, CartEntity foundCartEntity) {
//        OrdersEntity ordersEntity = new OrdersEntity();
//        ordersEntity.setCart(foundCartEntity);
//        ordersEntity.setAmount(ordersDto.getAmount());
//        ordersEntity.setStatus(ordersDto.getStatus());
//        return ordersEntity;
//    }
}
