package com.example.computershop.repository;

import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.computershop.data.DeviceViewData.createDeviceViewMock1;
import static com.example.computershop.data.DeviceViewData.createDeviceViewMock2;
import static com.example.computershop.data.PcEntityData.createPcEntity1;
import static com.example.computershop.data.PcEntityData.createPcEntity2;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static com.example.computershop.data.ProductEntityData.createProductEntity2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeviceRepositoryTest extends BaseIT {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PcRepository pcRepository;

    private ProductEntity productEntity;
    private PcEntity pcEntity;

    @BeforeEach
    void setUp() {
        productEntity = createProductEntity1();
        pcEntity = createPcEntity1();
    }

    @Test
    void findDeviceByCode() {
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        pcEntity.setProduct(savedProductEntity);
        PcEntity savedPcEntity = pcRepository.save(pcEntity);
        DeviceView expected = createDeviceViewMock1(savedPcEntity.getProduct().getModel());

        DeviceView actual = deviceRepository.findDeviceByCode(savedPcEntity.getProduct().getModel());

        assertEquals(expected.getModel(), actual.getModel());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    void findAllDevices() {
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        pcEntity.setProduct(savedProductEntity);
        PcEntity savedPcEntity = pcRepository.save(pcEntity);
        ProductEntity productEntity2 = createProductEntity2();
        ProductEntity savedProductEntity2 = productRepository.save(productEntity2);
        PcEntity pcEntity2 = createPcEntity2();
        pcEntity2.setProduct(savedProductEntity2);
        PcEntity savedPcEntity2 = pcRepository.save(pcEntity2);
        List<DeviceView> actual = deviceRepository.findAllDevices();

        List<DeviceView> expected = new ArrayList<>();
        expected.add(createDeviceViewMock1(savedPcEntity.getProduct().getModel()));
        expected.add(createDeviceViewMock2(savedPcEntity2.getProduct().getModel()));

        for(int i = 0; i < expected.size(); i++){
        assertEquals(expected.get(i).getModel(), actual.get(i).getModel());
        assertEquals(expected.get(i).getPrice(), actual.get(i).getPrice());
        }
    }

}
