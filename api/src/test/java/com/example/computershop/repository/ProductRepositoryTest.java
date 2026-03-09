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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductRepositoryTest extends RepositoryIT {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PcRepository pcRepository;

    @Test
    void findAllProductsJoined() {
        ProductEntity product1 = productRepository.save(createProductEntity1());
        PcEntity pc1 = createPcEntity1();
        pc1.setProduct(product1);
        PcEntity expected1 = pcRepository.save(pc1);

        ProductEntity product2 = productRepository.save(createProductEntity2());
        PcEntity pc2 = createPcEntity2();
        pc2.setProduct(product2);
        PcEntity expected2 = pcRepository.save(pc2);

        List<ProductJoinedView> actual = productRepository.findAllProductsJoined();

        assertThat(actual)
                .hasSize(2)
                .anySatisfy(actual1 -> {
                    assertEquals(expected1.getProduct().getModel(), actual1.getModel());
                    assertEquals(expected1.getProduct().getMaker(), actual1.getMaker());
                    assertEquals(expected1.getProduct().getType(), actual1.getType());
                    assertEquals(expected1.getCode(), actual1.getCode());
                })
                .anySatisfy(actual2 -> {
                    assertEquals(expected2.getProduct().getModel(), actual2.getModel());
                    assertEquals(expected2.getProduct().getMaker(), actual2.getMaker());
                    assertEquals(expected2.getProduct().getType(), actual2.getType());
                    assertEquals(expected2.getCode(), actual2.getCode());
                });
    }
}
