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
        List<CartEntity> cartEntities = cartRepository.findAll();
        return cartMapper.toCartDtoList(cartEntities);
    }

    public List<CartDto> getCartForUser(String username) {

        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Нет такого пользователя " + username));

        CartEntity cartEntity = cartRepository.findByUser(user);

        List<CartDeviceProductEntity> items =
                cartDeviceProductRepository.findByCartEntityIn(cartEntity);

        return items.stream()
                .map(item -> {
                    Integer price = getPriceByCodeAndModel(item.getCode(), item.getProductEntity().getModel());

                    CartDto dto = new CartDto();

                    dto.setCartId(item.getCartEntity().getCartId());
                    dto.setUsername(username);
                    dto.setModel(item.getProductEntity().getModel());
                    dto.setType(item.getProductEntity().getType());
                    dto.setCode(item.getCode());
                    dto.setPrice(price);

                    return dto;
                })
                .toList();
    }

    public CartDto save(CartDto requestCartDto) {
        UsersEntity foundUsersEntity = usersRepository.findByUsername(requestCartDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя: " + requestCartDto.getUsername()));

        CartEntity foundCartEntity = cartRepository.findByUserId(foundUsersEntity).orElse(null);

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

        return cartMapper.toCartDto(cartEntity);
    }


    @Transactional
    public void delete(String username) {

        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("нет такого пользователя: " + username));

        CartEntity cart = cartRepository.findByUser(user);

        cartDeviceProductRepository.deleteByCartEntityIn(cart);

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

    private CartEntity getCartEntity(CartDto cartDto) {
//        int price = productService.getPriceByCode(cartDto.getCode())
//                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + cartDto.getCode()));
//        ProductEntity foundProductEntity = productRepository.findById(cartDto.getModel())
//                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        UsersEntity foundUsersEntity = usersRepository.findByUsername(cartDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        CartEntity cartEntity = new CartEntity();
//        cartEntity.setProduct(foundProductEntity);
        cartEntity.setUser(foundUsersEntity);
//        cartEntity.setCode(cartDto.getCode());
//        cartEntity.setPrice(price);
        return cartEntity;
    }

    private Integer getPriceByCodeAndModel(Long code, String model) {
        return deviceRepository.findDevicePriceByCode(code, model)
                .map(DevicePriceView::getPrice).orElseThrow(() ->
                        new EntityNotFoundException("нет такого дивайса: " + code));
    }
}

