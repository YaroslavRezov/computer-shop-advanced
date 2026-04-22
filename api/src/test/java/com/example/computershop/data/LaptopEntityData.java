package com.example.computershop.data;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;

public class LaptopEntityData {

    public static LaptopEntity createLaptopEntity1() {
        return createLaptopEntity1("1298", 1L);
    }

    public static LaptopEntity createLaptopEntity1WithoutId() {
        return createLaptopEntity1(null, null);
    }

    public static LaptopEntity createLaptopEntity1(String model, Long code) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel(model);
        productEntity.setType("Laptop");

        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setProduct(productEntity);
        laptopEntity.setSpeed(350);
        laptopEntity.setRam(32);
        laptopEntity.setHd(4.0);
        laptopEntity.setPrice(700);
        laptopEntity.setScreen(11);
        laptopEntity.setCode(code);
        return laptopEntity;
    }

    public static LaptopEntity createLaptopEntity2() {
        return createLaptopEntity2("74af7054-64f5-46b3-8a98-a0e16e0f9554", 8L);
    }

    public static LaptopEntity createLaptopEntity2WithoutId() {
        return createLaptopEntity2(null, null);
    }

    public static LaptopEntity createLaptopEntity2(String model, Long code) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel(model);
        productEntity.setType("Laptop");

        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setProduct(productEntity);
        laptopEntity.setSpeed(1122);
        laptopEntity.setRam(1122);
        laptopEntity.setHd(11.0);
        laptopEntity.setPrice(112211);
        laptopEntity.setScreen(11);
        laptopEntity.setCode(code);
        return laptopEntity;
    }

    public static LaptopEntity createLaptopEntity(ProductEntity product, int speed, int ram, double hd, int price, int screen) {
        LaptopEntity laptop = new LaptopEntity();
        laptop.setProduct(product);
        laptop.setSpeed(speed);
        laptop.setRam(ram);
        laptop.setHd(hd);
        laptop.setPrice(price);
        laptop.setScreen(screen);
        return laptop;
    }
}
