package com.example.computershop.mapper;

import com.example.computershop.model.entity.*;
import com.example.computershop.repository.DeviceRepository;
import com.example.specs.generated.model.OrdersDevicesDto;
import com.example.specs.generated.model.OrdersDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class OrdersMapper {

    private final DeviceRepository deviceRepository;

    public List<OrdersDto> toOrdersDtoList(List<OrdersEntity> orders) {
        return orders.stream()
                .map(this::toOrdersDto)
                .toList();
    }

    public OrdersDto toOrdersDto(OrdersEntity order) {

        CartEntity cart = order.getCartEntity();

        List<OrdersDevicesDto> items = cart.getDevices()
                .stream()
                .map(this::toOrdersDevicesDto)
                .toList();

        long amount = calculateAmount(cart);

        return new OrdersDto()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .username(cart.getUser().getUsername())
                .email(cart.getUser().getEmail())
                .amount(amount)
                .ordersDevices(items);
    }

    private OrdersDevicesDto toOrdersDevicesDto(
            CartDeviceProductEntity item) {

        return new OrdersDevicesDto()
                .code(item.getCode())
                .model(item.getProductEntity().getModel());
    }

    private long calculateAmount(CartEntity cart) {
        return cart.getDevices()
                .stream()
                .mapToLong(this::getPrice)
                .sum();
    }

    private int getPrice(CartDeviceProductEntity item) {
        return deviceRepository
                .findDevicePriceByCodeAndModel(
                        item.getCode(),
                        item.getProductEntity().getModel())
                .map(DevicePriceView::getPrice)
                .orElseThrow(() ->
                        new EntityNotFoundException("нет такого дивайса"));
    }
}



//    public List<OrdersDto> toOrdersDtoList(List<OrdersView> ordersViews) {
//        return ordersViews.stream()
//                .map(this::toOrdersDto)
//                .toList();
//    }
//
//    public OrdersDto toOrdersDto(OrdersView ordersView) {
//        return new OrdersDto()
//                .orderId(ordersView.getOrderId())
//                .code(ordersView.getCode())
//                .model(ordersView.getModel())
//                .amount(ordersView.getAmount())
//                .status(ordersView.getStatus())
//                .username(ordersView.getUsername())
//                .email(ordersView.getEmail());
//    }
//
//
//    public OrdersEntity toOrdersEntity(CartEntity cartEntity) {
//        OrdersEntity ordersEntity = new OrdersEntity();
//        ordersEntity.setCartEntity(cartEntity);
//        return ordersEntity;
//    }


