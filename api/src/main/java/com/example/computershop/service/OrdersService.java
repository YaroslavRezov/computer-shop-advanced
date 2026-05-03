package com.example.computershop.service;

import com.example.computershop.mapper.OrdersMapper;
import com.example.computershop.model.entity.*;
import com.example.computershop.repository.CartRepository;
import com.example.computershop.repository.OrdersRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.repository.UsersRepository;
import com.example.specs.generated.model.OrdersDto;
import com.example.specs.generated.model.PcDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;

    public OrdersDto getOrder(Long id) {
        OrdersEntity ordersEntity = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет такого заказа"));
        return ordersMapper.toOrdersDto(ordersEntity);
    }

    public OrdersDto save(OrdersDto requestOrdersDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestOrdersDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        UsersEntity foundUsersEntity = usersRepository.findById(requestOrdersDto.getUser())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
        OrdersEntity sourceOrdersEntity = ordersMapper.toOrdersEntity(requestOrdersDto, foundProductEntity, foundUsersEntity, foundCartEntity);
        OrdersEntity savedOrdersEntity = ordersRepository.save(sourceOrdersEntity);
        return ordersMapper.toOrdersDto(savedOrdersEntity);
    }

    public OrdersDto updateOrdersPartially(Long ordersId, OrdersDto requestOrdersDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestOrdersDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        UsersEntity foundUsersEntity = usersRepository.findById(requestOrdersDto.getUser())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
        OrdersEntity setOrdersEntity = ordersRepository.findById(ordersId)
                .orElseThrow(() -> new RuntimeException("Нет такого заказа"));
        setOrdersEntity.setCart(foundCartEntity);
        setOrdersEntity.setAmount(requestOrdersDto.getAmount());
        setOrdersEntity.setUsersEmail(foundUsersEntity);
        setOrdersEntity.setNumber(setOrdersEntity.getNumber());
        setOrdersEntity.setProduct(foundProductEntity);
        setOrdersEntity.setCode(requestOrdersDto.getCode());
        setOrdersEntity.setUser(foundUsersEntity);
        setOrdersEntity.setStatus("done");
        OrdersEntity savedOrdersEntity = ordersRepository.save(setOrdersEntity);
        return ordersMapper.toOrdersDto(savedOrdersEntity);
    }


}
