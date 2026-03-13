package com.example.computershop.service;

import com.example.computershop.mapper.ProductMapper;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.CartDto;
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
import static com.example.computershop.data.ProductJoinedDtoData.createProductJoinedDto1;
import static com.example.computershop.data.ProductJoinedViewData.createProductJoinedViewMock1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringJUnitConfig(classes = {ProductService.class})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private PcRepository pcRepository;

    @MockBean
    private PrinterRepository printerRepository;

    @MockBean
    private LaptopRepository laptopRepository;

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
        when(productMapper.toProductDtoList(any())).thenReturn(List.of(productDto));

        List<ProductDto> actual = productService.getProducts();

        verify(productRepository, times(1)).findAll();
        verify(productMapper).toProductDtoList(List.of(productEntity));
        assertEquals(1, actual.size());
    }

    @Test
    public void getProduct() {
        ProductEntity productEntity = createProductEntity1();
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        ProductDto expected = createProductDto1();
        when(productMapper.toProductDto(any())).thenReturn(expected);

        ProductDto actual = productService.getProduct(productEntity.getModel());

        verify(productRepository).findById(productEntity.getModel());
        verify(productMapper).toProductDto(productEntity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getProduct_whenProductNotFound_shouldThrowException() {
        String model = "sosal?";
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProduct(model));

        verify(productRepository).findById(model);
        verify(productMapper, never()).toProductDto(any());
        assertEquals("Нет такого продукта", exception.getMessage());
    }

    @Test
    public void getAllProductsJoined() {
        productJoinedDto = createProductJoinedDto1();
        productJoinedView = createProductJoinedViewMock1();
        List<ProductJoinedView> productJoinedViews = Arrays.asList(productJoinedView);
        List<ProductJoinedDto> expected = Arrays.asList(productJoinedDto);


        when(productRepository.findAllProductsJoined()).thenReturn(productJoinedViews);
        when(productMapper.toProductJoinedDtoList(any())).thenReturn(expected);

        List<ProductJoinedDto> actual = productService.getAllProductsJoined();

        verify(productRepository).findAllProductsJoined();
        verify(productMapper).toProductJoinedDtoList(productJoinedViews);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void save() {
        when(productMapper.toProductEntity(any())).thenReturn(productEntity);
        when(productRepository.save(any())).thenReturn(productEntity);
        when(productMapper.toProductDto(any())).thenReturn(productDto);

        ProductDto actual = productService.save(productDto);

        verify(productMapper).toProductEntity(productDto);
        verify(productRepository).save(productEntity);
        verify(productMapper).toProductDto(productEntity);
        assertThat(actual).isEqualTo(productDto);
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

        when(productRepository.findById(any())).thenReturn(Optional.of(existingEntity));
        when(productRepository.save(any())).thenReturn(updatedEntity);
        when(productMapper.toProductDto(any())).thenReturn(expectedDto);

        ProductDto actual = productService.updateProductPartially(existingEntity.getModel(), updateDto);

        verify(productRepository).findById(existingEntity.getModel());
        verify(productRepository).save(existingEntity);
        verify(productMapper).toProductDto(updatedEntity);

        assertThat(actual).isEqualTo(expectedDto);
    }

    @Test
    void updateProductPartially_whenProductNotFound_shouldThrowException() {
        ProductDto updateDto = new ProductDto();
        updateDto.setMaker("B");
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.updateProductPartially("sosal?", updateDto));

        assertEquals("Нет такого продукта", exception.getMessage());

        verify(productRepository).findById("sosal?");
        verify(productRepository, never()).save(any());
        verify(productMapper, never()).toProductDto(any());
    }

    @Test
    void delete() {
        doNothing().when(productRepository).deleteById("1232");

        productService.delete("1232");

        verify(productRepository).deleteById("1232");
    }

}
