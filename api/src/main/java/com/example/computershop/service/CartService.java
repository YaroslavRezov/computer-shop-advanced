package com.example.computershop.service;

import com.example.computershop.mapper.CartMapper;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;
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
        CartEntity cartEntity = getCartEntity(requestCartDto);
        CartEntity savedCartEntity = cartRepository.save(cartEntity);
        return cartMapper.toCartDto(savedCartEntity);
    }

    public void delete(String username){
        UsersEntity foundUsersEntity = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        cartRepository.deleteByUser(foundUsersEntity);
    }

    public void delete(Long orderId){
        cartRepository.deleteById(orderId);
    }

    private CartEntity getCartEntity(CartDto cartDto) {
        int price = productService.getPriceByCode(cartDto.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Price not found for code " + cartDto.getCode()));
        ProductEntity foundProductEntity = productRepository.findById(cartDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        UsersEntity foundUsersEntity = usersRepository.findByUsername(cartDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(foundProductEntity);
        cartEntity.setUser(foundUsersEntity);
        cartEntity.setCode(cartDto.getCode());
        cartEntity.setPrice(price);
        return cartEntity;
    }


}

