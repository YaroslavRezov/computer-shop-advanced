package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.example.computershop.data.CartEntityData.createCartEntity1;
import static com.example.computershop.data.CartEntityData.createCartEntity2;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static com.example.computershop.data.UsersEntityData.createUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartRepositoryTest extends BaseIT {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    private UsersEntity user;
    private ProductEntity product;

    @BeforeEach
    void setUp() {
        user = usersRepository.save(createUser1());
        product = productRepository.save(createProductEntity1());
    }

    @Test
    void findByUser() {
        CartEntity expectedCart1 = cartRepository.save(createCartEntity1(product, user));
        CartEntity expectedCart2 = cartRepository.save(createCartEntity2(product, user));

        List<CartEntity> actual = cartRepository.findByUser(user);

        assertEquals(2, actual.size(), "Должно быть 2 элемента в корзине");
        assertEquals(List.of(expectedCart1, expectedCart2), actual);
    }

    @Test
    void deleteByUser() {
        cartRepository.save(createCartEntity1(product, user));
        cartRepository.save(createCartEntity2(product, user));

        cartRepository.deleteByUser(user);

        List<CartEntity> actual = cartRepository.findAll();
        assertTrue(actual.isEmpty(), "Корзина должна быть пустой после удаления");
    }

}