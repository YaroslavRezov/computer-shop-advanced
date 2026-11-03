package com.example.computershop.service;

import com.example.computershop.mapper.CartMapper;
import com.example.computershop.model.entity.*;
import com.example.computershop.repository.*;
import com.example.specs.generated.model.CartDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final CartMapper cartMapper;
    private final CartDeviceProductRepository cartDeviceProductRepository;
    private final DeviceRepository deviceRepository;

    public List<CartDto> getAll() {
        List<CartDeviceProductEntity> cartEntities = cartDeviceProductRepository.findAll();
        return cartMapper.toCartDtoList(cartEntities);
    }

    public List<CartDto> getCartForUser(String username) {
        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя: " + username));

        CartEntity cartEntity = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Нет такой корзины"));

        List<CartDeviceProductEntity> cartDeviceProductEntities = cartDeviceProductRepository.findByCartEntity(cartEntity);

        return cartMapper.toCartDtoList(cartDeviceProductEntities);
    }

    public CartDto save(CartDto requestCartDto) {
        System.out.println("save() called");
        UsersEntity foundUsersEntity = usersRepository.findByUsername(requestCartDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя: " + requestCartDto.getUsername()));

        CartEntity foundCartEntity = cartRepository.findByUser(foundUsersEntity).orElse(null);

        CartEntity cartEntity;
        if (foundCartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setUser(foundUsersEntity);
            cartEntity = cartRepository.save(cartEntity);
        } else {
            cartEntity = foundCartEntity;
        }

        ProductEntity foundProductEntity = productRepository.findById(requestCartDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта: " + requestCartDto.getModel()));

        CartDeviceProductEntity cartDeviceProductEntity = new CartDeviceProductEntity();
        cartDeviceProductEntity.setProductEntity(foundProductEntity);
        cartDeviceProductEntity.setCode(requestCartDto.getCode());
        cartDeviceProductEntity.setCartEntity(cartEntity);

        cartDeviceProductRepository.save(cartDeviceProductEntity);

        return cartMapper.toCartDto(cartDeviceProductEntity);
    }


    @Transactional
    public void delete(String username) {

        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("нет такого пользователя: " + username));

        CartEntity cartEntity = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Нет такой корзины"));

        cartDeviceProductRepository.deleteByCartEntity(cartEntity);

        cartRepository.deleteByUser(user);
    }

    @Transactional
    public void delete(Long cartId) {

        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException("нет такой карзины: " + cartId);
        }

        cartDeviceProductRepository.deleteByCartEntityCartId(cartId);

        cartRepository.deleteById(cartId);
    }
}

