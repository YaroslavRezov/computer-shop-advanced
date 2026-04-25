package com.example.computershop.data;

import com.example.computershop.model.entity.ProductEntity;
public class ProductEntityData {

    public static ProductEntity createProductEntity1WithoutId() {
        return createProductEntity1(null);
    }

    public static ProductEntity createProductEntity1() {
        return createProductEntity1("1232");
    }

    public static ProductEntity createProductEntity1(String model) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel(model);
        productEntity.setType("PC");
        return productEntity;
    }

    public static ProductEntity createProductEntity2WithoutId() {
        return createProductEntity2(null);
    }

    public static ProductEntity createProductEntity2() {
        return createProductEntity2("b276a11d-c526-4f74-b3c7-95ff94bf7147");
    }

    public static ProductEntity createProductEntity2(String model) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("bbbruu");
        productEntity.setModel(model);
        productEntity.setType("PC");
        return productEntity;
    }

    public static ProductEntity createProductEntity(String maker, String type) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker(maker);
        productEntity.setType(type);
        return productEntity;
    }
}
