package com.example.computershop.data;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;

public class LaptopEntityData {

    public static LaptopEntity createLaptopEntity1() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("1298");
        productEntity.setType("Laptop");

        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setProduct(productEntity);
        laptopEntity.setSpeed(350);
        laptopEntity.setRam(32);
        laptopEntity.setHd(4.0);
        laptopEntity.setPrice(700);
        laptopEntity.setScreen(11);
        laptopEntity.setCode(1L);
        return laptopEntity;
    }

    public static LaptopEntity createLaptopEntity2() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("74af7054-64f5-46b3-8a98-a0e16e0f9554");
        productEntity.setType("Laptop");

        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setProduct(productEntity);
        laptopEntity.setSpeed(1122);
        laptopEntity.setRam(1122);
        laptopEntity.setHd(11.0);
        laptopEntity.setPrice(112211);
        laptopEntity.setScreen(11);
        laptopEntity.setCode(8L);
        return laptopEntity;
    }
}
