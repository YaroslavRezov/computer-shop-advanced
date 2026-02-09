package com.example.computershop.repository;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.computershop.data.PcEntityData.createPcEntity1;
import static com.example.computershop.data.PcEntityData.createPcEntity2;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static com.example.computershop.data.ProductEntityData.createProductEntity2;
import static com.example.computershop.data.ProductJoinedViewData.createProductJoinedViewMock1;
import static com.example.computershop.data.ProductJoinedViewData.createProductJoinedViewMock2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductRepositoryTest extends BaseIT{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PcRepository pcRepository;
    @Test
    void findAllProductsJoined() {

        ProductEntity productEntity = createProductEntity1();

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        PcEntity pcEntity = createPcEntity1();
        pcEntity.setProduct(savedProductEntity);

        PcEntity savedPcEntity = pcRepository.save(pcEntity);


        ProductEntity productEntity2 = createProductEntity2();

        ProductEntity savedProductEntity2 = productRepository.save(productEntity2);

        PcEntity pcEntity2 = createPcEntity2();
        pcEntity2.setProduct(savedProductEntity2);

        PcEntity savedPcEntity2 = pcRepository.save(pcEntity2);

        List<ProductJoinedView> actual = List.of(createProductJoinedViewMock1(savedPcEntity.getProduct().getModel(), savedPcEntity.getCode()),
                createProductJoinedViewMock2(savedPcEntity2.getProduct().getModel(), savedPcEntity2.getCode()));
        List<ProductJoinedView> expected = productRepository.findAllProductsJoined();
        for(int i = 0; i < actual.size(); i++){
            assertEquals(expected.get(i).getModel(), actual.get(i).getModel());
            assertEquals(expected.get(i).getMaker(), actual.get(i).getMaker());
            assertEquals(expected.get(i).getType(), actual.get(i).getType());
            assertEquals(expected.get(i).getCode(), actual.get(i).getCode());
        }
    }
}
