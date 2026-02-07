package com.example.computershop.data;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
public class PcEntityData {

    public static PcEntity createPcEntity1() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("1232");
        productEntity.setType("PC");

        PcEntity pcEntity = new PcEntity();
        pcEntity.setProduct(productEntity);
        pcEntity.setSpeed(500);
        pcEntity.setRam(64);
        pcEntity.setHd(5.0);
        pcEntity.setCd("12x");
        pcEntity.setPrice(600);
        pcEntity.setCode(1L);
        return pcEntity;
    }

    public static PcEntity createPcEntity2() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("bbbruu");
        productEntity.setModel("b276a11d-c526-4f74-b3c7-95ff94bf7147");
        productEntity.setType("PC");

        PcEntity pcEntity = new PcEntity();
        pcEntity.setProduct(productEntity);
        pcEntity.setSpeed(11111);
        pcEntity.setRam(11111);
        pcEntity.setHd(111.0);
        pcEntity.setCd("12");
        pcEntity.setPrice(11111111);
        pcEntity.setCode(29L);
        return pcEntity;
    }
}
