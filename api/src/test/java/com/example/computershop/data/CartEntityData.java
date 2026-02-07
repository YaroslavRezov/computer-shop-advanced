package com.example.computershop.data;

import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;

public class CartEntityData {

    public static CartEntity createCartEntity1() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("1276");
        productEntity.setType("PC");

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername("Omen");
        usersEntity.setPassword("0000");
        usersEntity.setId(27L);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setUser(usersEntity);
        cartEntity.setOrderId(103L);
        cartEntity.setCode(1L);
        cartEntity.setPrice(600);

        return cartEntity;
    }

    public static CartEntity createCartEntity2() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("b276a11d-c526-4f74-b3c7-95ff94bf7147");
        productEntity.setType("PC");

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername("Omen");
        usersEntity.setPassword("0000");
        usersEntity.setId(27L);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setUser(usersEntity);
        cartEntity.setOrderId(104L);
        cartEntity.setCode(31L);
        cartEntity.setPrice(111111);
        return cartEntity;
    }

    public static CartEntity createCartEntity1(ProductEntity productEntity, UsersEntity usersEntity) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setUser(usersEntity);
        cartEntity.setOrderId(103L);
        cartEntity.setCode(1L);
        cartEntity.setPrice(600);
        return cartEntity;
    }

    public static CartEntity createCartEntity2(ProductEntity productEntity, UsersEntity usersEntity) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setUser(usersEntity);
        cartEntity.setOrderId(104L);
        cartEntity.setCode(31L);
        cartEntity.setPrice(111111);
        return cartEntity;
    }
}
