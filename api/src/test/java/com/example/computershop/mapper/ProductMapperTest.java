package com.example.computershop.mapper;

import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.example.computershop.data.ProductDtoData.createProductDto1;
import static com.example.computershop.data.ProductDtoData.createProductDto2;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static com.example.computershop.data.ProductEntityData.createProductEntity2;
import static com.example.computershop.data.ProductJoinedDtoData.createProductJoinedDto1;
import static com.example.computershop.data.ProductJoinedDtoData.createProductJoinedDto2;
import static com.example.computershop.data.ProductJoinedViewData.createProductJoinedViewMock1;
import static com.example.computershop.data.ProductJoinedViewData.createProductJoinedViewMock2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {ProductMapper.class})
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void toProductDto() {
        ProductEntity source = createProductEntity1();
        ProductDto expected = createProductDto1();

        ProductDto actual = productMapper.toProductDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toProductDtoList() {
        List<ProductEntity> source = List.of(createProductEntity1(), createProductEntity2());
        List<ProductDto> expected = List.of(createProductDto1(), createProductDto2());

        List<ProductDto> actual = productMapper.toProductDtoList(source);

        assertEquals(expected, actual);
    }

    @Test
    void toProductJoinedDtoList() {
        List<ProductJoinedView> source = List.of(createProductJoinedViewMock1(), createProductJoinedViewMock2());
        List<ProductJoinedDto> expected = List.of(createProductJoinedDto1(), createProductJoinedDto2());

        List<ProductJoinedDto> actual = productMapper.toProductJoinedDtoList(source);

        assertEquals(expected, actual);
    }

}
