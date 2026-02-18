package com.example.computershop.service;

import com.example.computershop.mapper.ProductMapper;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.computershop.data.ProductDtoData.createProductDto1;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringJUnitConfig(classes = {ProductService.class})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    private ProductEntity productEntity;
    private ProductDto productDto;
    private ProductJoinedView productJoinedView;
    private ProductJoinedDto productJoinedDto;

    @BeforeEach
    void setUp() {
        productEntity = new ProductEntity();
        productDto = new ProductDto();
    }

    @Test
    public void getProducts() {
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.toProductDtoList(List.of(productEntity))).thenReturn(List.of(productDto));

        List<ProductEntity> actual = productRepository.findAll();

        assertEquals(1, actual.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProduct() {
        ProductEntity productEntity = createProductEntity1();
        when(productRepository.findById(productEntity.getModel())).thenReturn(Optional.of(productEntity));
        ProductDto expected = createProductDto1();
        when(productMapper.toProductDto(productEntity)).thenReturn(expected);

        ProductDto actual = productService.getProduct(productEntity.getModel());

        assertThat(actual).isEqualTo(expected);
        verify(productRepository).findById(productEntity.getModel());
        verify(productMapper).toProductDto(productEntity);
    }
    @Test
    public void getAllProductsJoined() {
        List<ProductJoinedView> productJoinedViews = Arrays.asList(productJoinedView);
        List<ProductJoinedDto> expected = Arrays.asList(productJoinedDto);

        when(productRepository.findAllProductsJoined()).thenReturn(productJoinedViews);
        when(productMapper.toProductJoinedDtoList(productJoinedViews)).thenReturn(expected);

        List<ProductJoinedDto> actual = productService.getAllProductsJoined();

        assertThat(actual).isEqualTo(expected);
        verify(productRepository).findAllProductsJoined();
        verify(productMapper).toProductJoinedDtoList(productJoinedViews);
    }

    @Test
    void save() {
        when(productMapper.toProductEntity(productDto)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toProductDto(productEntity)).thenReturn(productDto);

        ProductDto actual = productService.save(productDto);

        assertThat(actual).isEqualTo(productDto);
        verify(productMapper).toProductEntity(productDto);
        verify(productRepository).save(productEntity);
        verify(productMapper).toProductDto(productEntity);
    }

    @Test
    void update() {
        ProductDto updateDto = new ProductDto();
        updateDto.setMaker("B");

        ProductEntity existingEntity = createProductEntity1();

        ProductEntity updatedEntity = new ProductEntity();
        updatedEntity.setModel(existingEntity.getModel());
        updatedEntity.setMaker("B");

        ProductDto expectedDto = new ProductDto();
        expectedDto.setModel(existingEntity.getModel());
        expectedDto.setMaker("B");

        when(productRepository.findById(existingEntity.getModel())).thenReturn(Optional.of(existingEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedEntity);
        when(productMapper.toProductDto(updatedEntity)).thenReturn(expectedDto);

        ProductDto actual = productService.updateProductPartially(existingEntity.getModel(), updateDto);

        assertThat(actual).isEqualTo(expectedDto);
        assertThat(actual.getMaker()).isEqualTo("B");
        verify(productRepository).findById(existingEntity.getModel());
        verify(productRepository).save(existingEntity);
        verify(productMapper).toProductDto(updatedEntity);

        assertThat(existingEntity.getMaker()).isEqualTo("B");
    }

    @Test
    void delete() {
        doNothing().when(productRepository).deleteById("1232");

        productService.delete("1232");

        verify(productRepository).deleteById("1232");
    }

}
