package com.example.computershop.service;

import com.example.computershop.mapper.CartMapper;
import com.example.computershop.mapper.OrdersMapper;
import com.example.computershop.model.entity.*;
import com.example.computershop.repository.*;
import com.example.specs.generated.model.OrdersDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final CartDeviceProductRepository cartDeviceProductRepository;
    private final CartMapper cartMapper;

    public List<OrdersDto> getOrders(String username) {
        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя: " + username));
        List<OrdersEntity> ordersEntities = ordersRepository.findByUser(user);

        return ordersMapper.toOrdersDtoList(ordersEntities);
    }

    @Transactional
    public OrdersDto save(String username) {
        UsersEntity foundUserEntity = usersRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Нет такого пользователя: " + username));

        CartEntity foundCartEntity = cartRepository.findByUserId(foundUserEntity)
                .orElseThrow(() -> new EntityNotFoundException("Нет такой корзины"));

        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setCartEntity(foundCartEntity);

        OrdersEntity savedOrdersEntity = ordersRepository.save(ordersEntity);

        return ordersMapper.toOrdersDto(savedOrdersEntity);
    }

//    public List<OrdersDto> getOrders(String username) {
//        return ordersMapper.toOrdersDtoList(ordersRepository.findByUsername(username));
//    }

//
//    public OrdersDto save(OrdersDto requestOrdersDto) {
//        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
//        OrdersEntity sourceOrdersEntity = ordersMapper.toOrdersEntity(foundCartEntity);
//        OrdersEntity savedOrdersEntity = ordersRepository.save(sourceOrdersEntity);
//
//        return ordersMapper.toOrdersDto(savedOrdersEntity);
//    }
//        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
//        OrdersEntity sourceOrdersEntity = ordersMapper.toOrdersEntity(requestOrdersDto, foundCartEntity);
//        sourceOrdersEntity.setOrderId();
//        OrdersEntity savedOrdersEntity = ordersRepository.save(sourceOrdersEntity);
//        return ordersMapper.toOrdersDto(savedOrdersEntity);
//    }
//    private OrdersEntity setOrderIdWhenButtonPressed(OrdersEntity ordersEntity) {
//
//    }
//
//    public OrdersDto save(OrdersDto requestOrdersDto) {
//        ProductEntity foundProductEntity = productRepository.findById(requestOrdersDto.getModel())
//                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
//        UsersEntity foundUsersEntity = usersRepository.findById(requestOrdersDto.getUser())
//                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
//        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
//        OrdersEntity sourceOrdersEntity = ordersMapper.toOrdersEntity(requestOrdersDto, foundProductEntity, foundUsersEntity, foundCartEntity);
//        OrdersEntity savedOrdersEntity = ordersRepository.save(sourceOrdersEntity);
//        return ordersMapper.toOrdersDto(savedOrdersEntity);
//    }

//    public OrdersDto updateOrdersPartially(Long ordersId, OrdersDto requestOrdersDto) {
//        ProductEntity foundProductEntity = productRepository.findById(requestOrdersDto.getModel())
//                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
//        UsersEntity foundUsersEntity = usersRepository.findById(requestOrdersDto.getUser())
//                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
//        CartEntity foundCartEntity = cartRepository.findById(requestOrdersDto.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Нет такого карзины"));
//        OrdersEntity setOrdersEntity = ordersRepository.findById(ordersId)
//                .orElseThrow(() -> new RuntimeException("Нет такого заказа"));
//        setOrdersEntity.setCart(foundCartEntity);
//        setOrdersEntity.setAmount(requestOrdersDto.getAmount());
//        setOrdersEntity.setUsersEmail(foundUsersEntity);
//        setOrdersEntity.setNumber(setOrdersEntity.getNumber());
//        setOrdersEntity.setProduct(foundProductEntity);
//        setOrdersEntity.setCode(requestOrdersDto.getCode());
//        setOrdersEntity.setUser(foundUsersEntity);
//        setOrdersEntity.setStatus("done");
//        OrdersEntity savedOrdersEntity = ordersRepository.save(setOrdersEntity);
//        return ordersMapper.toOrdersDto(savedOrdersEntity);
//    }



}
