package com.example.computershop.data;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
public class PcEntityData {

    public static PcEntity createPcEntity1() {
        return createPcEntity1("1232", 1L);
    }

    public static PcEntity createPcEntity1WithoutId() {
        return createPcEntity1(null, null);
    }

    public static PcEntity createPcEntity1(String model, Long code) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel(model);
        productEntity.setType("PC");

        PcEntity pcEntity = new PcEntity();
        pcEntity.setProduct(productEntity);
        pcEntity.setSpeed(500);
        pcEntity.setRam(64);
        pcEntity.setHd(5.0);
        pcEntity.setCd("12x");
        pcEntity.setPrice(600);
        pcEntity.setCode(code);
        return pcEntity;
    }

    public static PcEntity createPcEntity2() {
        return createPcEntity2("b276a11d-c526-4f74-b3c7-95ff94bf7147", 29L);
    }

    public static PcEntity createPcEntity2WithoutId() {
        return createPcEntity2(null, null);
    }

    public static PcEntity createPcEntity2(String model, Long code) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("bbbruu");
        productEntity.setModel(model);
        productEntity.setType("PC");

        PcEntity pcEntity = new PcEntity();
        pcEntity.setProduct(productEntity);
        pcEntity.setSpeed(11111);
        pcEntity.setRam(11111);
        pcEntity.setHd(111.0);
        pcEntity.setCd("12");
        pcEntity.setPrice(11111111);
        pcEntity.setCode(code);
        return pcEntity;
    }

    public static PcEntity createPcEntity(ProductEntity product, int speed, int ram, double hd, String cd, int price) {
        PcEntity pc = new PcEntity();
        pc.setProduct(product);
        pc.setSpeed(speed);
        pc.setRam(ram);
        pc.setHd(hd);
        pc.setCd(cd);
        pc.setPrice(price);
        return pc;
    }
}
