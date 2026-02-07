package com.example.computershop.data;

import com.example.computershop.model.entity.ProductEntity;
public class ProductEntityData {

    public static ProductEntity createProductEntity1() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("1232");
        productEntity.setType("PC");
        return productEntity;
    }

    public static ProductEntity createProductEntity2() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("bbbruu");
        productEntity.setModel("b276a11d-c526-4f74-b3c7-95ff94bf7147");
        productEntity.setType("PC");
        return productEntity;
    }
}
