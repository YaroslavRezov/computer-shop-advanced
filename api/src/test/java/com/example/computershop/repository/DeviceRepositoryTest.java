package com.example.computershop.repository;

import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.example.computershop.data.DeviceViewData.createDeviceViewMock1;
import static com.example.computershop.data.DeviceViewData.createDeviceViewMock2;
import static com.example.computershop.data.PcEntityData.createPcEntity1WithoutId;
import static com.example.computershop.data.PcEntityData.createPcEntity2WithoutId;
import static com.example.computershop.data.ProductEntityData.createProductEntity1WithoutId;
import static com.example.computershop.data.ProductEntityData.createProductEntity2WithoutId;
import static org.assertj.core.api.Assertions.assertThat;

public class DeviceRepositoryTest extends RepositoryIT {

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
        productEntity = productRepository.save(createProductEntity1WithoutId());
        pcEntity = createPcEntity1WithoutId();
        pcEntity.setProduct(productEntity);
        pcEntity = pcRepository.save(pcEntity);
    }

    @AfterEach
    void cleanUp() {
        pcRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    void findDeviceByCode() {
        DeviceView expected = createDeviceViewMock1(pcEntity.getProduct().getModel());

        DeviceView actual = deviceRepository.findDeviceByCode(pcEntity.getProduct().getModel());

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void findAllDevices() {
        ProductEntity productEntity2 = createProductEntity2WithoutId();
        ProductEntity savedProductEntity2 = productRepository.save(productEntity2);
        PcEntity pcEntity2 = createPcEntity2WithoutId();
        pcEntity2.setProduct(savedProductEntity2);
        PcEntity savedPcEntity2 = pcRepository.save(pcEntity2);
        List<DeviceView> expected = new ArrayList<>();

        List<DeviceView> actual = deviceRepository.findAllDevices();
        expected.add(createDeviceViewMock1(pcEntity.getProduct().getModel()));
        expected.add(createDeviceViewMock2(savedPcEntity2.getProduct().getModel()));

        assertThat(actual)
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
