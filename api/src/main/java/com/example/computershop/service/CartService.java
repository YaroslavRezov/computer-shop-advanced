package com.example.computershop.service;

import com.example.computershop.mapper.CartMapper;
import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.repository.CartDeviceProductRepository;
import com.example.computershop.repository.CartRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.repository.UsersRepository;
import com.example.specs.generated.model.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final CartMapper cartMapper;
    private final CartDeviceProductRepository cartDeviceProductRepository;

    public List<CartDto> getAll() {
        List<CartEntity> cartEntities = cartRepository.findAll();
        return cartMapper.toCartDtoList(cartEntities);
    }

    public List<CartDto> getCartForUser(String username){
        UsersEntity usersEntity = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя" + username));
        List<CartEntity> cartEntities = cartRepository.findByUser(usersEntity);
        return cartMapper.toCartDtoList(cartEntities);
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
        cartDeviceProductEntity.setCartId(cartEntity);

        cartDeviceProductRepository.save(cartDeviceProductEntity);

        return cartMapper.toCartDto(cartEntity);
    }


    public void delete(String username){
        UsersEntity foundUsersEntity = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        cartRepository.deleteByUser(foundUsersEntity);
    }

    public void delete(Long cartId){
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


}

