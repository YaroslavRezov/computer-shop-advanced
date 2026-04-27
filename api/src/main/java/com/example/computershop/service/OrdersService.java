package com.example.computershop.service;

import com.example.computershop.mapper.OrdersMapper;
import com.example.computershop.model.entity.OrdersEntity;
import com.example.computershop.repository.OrdersRepository;
import com.example.specs.generated.model.OrdersDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    
    public OrdersDto getOrder(Long id) {
        OrdersEntity ordersEntity = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет такого заказа"));
        return ordersMapper.toOrdersDto(ordersEntity);
    }
}
