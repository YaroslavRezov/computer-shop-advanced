package com.example.computershop.data;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;

public class PrinterEntityData {

    public static PrinterEntity createPrinterEntity1() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("1276");
        productEntity.setType("Printer");

        PrinterEntity printerEntity = new PrinterEntity();
        printerEntity.setProduct(productEntity);
        printerEntity.setColor("n");
        printerEntity.setType("Laser");
        printerEntity.setPrice(400);
        printerEntity.setCode(1L);
        return printerEntity;
    }

    public static PrinterEntity createPrinterEntity2() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker("A");
        productEntity.setModel("88165743-dad3-486f-adf1-0abd207c2ef6");
        productEntity.setType("Printer");

        PrinterEntity printerEntity = new PrinterEntity();
        printerEntity.setProduct(productEntity);
        printerEntity.setColor("y");
        printerEntity.setType("lol");
        printerEntity.setPrice(7);
        printerEntity.setCode(13L);
        return printerEntity;
    }
}
