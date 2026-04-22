package com.example.computershop.controller;

import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.utils.TestUtils;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.example.computershop.data.ProductDtoData.createProductDto;
import static com.example.computershop.data.ProductEntityData.*;
import static com.example.computershop.utils.TestUtils.asList;
import static com.example.computershop.utils.TestUtils.asObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerIT extends ControllerIT {

    @Autowired
    private ProductRepository productRepository;
    private ProductEntity productEntity1;
    private ProductEntity productEntity2;

    @BeforeEach
    void setUp() {
        productEntity1 = createProductEntity("A", "PC");
        productEntity2 = createProductEntity("A", "Laptop");
        productEntity1 = productRepository.save(productEntity1);
        productEntity2 = productRepository.save(productEntity2);
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAllInBatch();
    }

    @Test
    void getProducts() throws Exception {
        ProductDto expected1 = createProductDto(productEntity1.getModel(), productEntity1.getMaker(), productEntity1.getType());
        ProductDto expected2 = createProductDto(productEntity2.getModel(), productEntity2.getMaker(), productEntity2.getType());

        MvcResult result = mockMvc.perform(get("/admin/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<ProductDto> actual = asList(responseBody, ProductDto.class);

        assertThat(actual)
                .hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("model")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(expected1, expected2));
    }

    @Test
    void getProduct() throws Exception {
        ProductDto expected = createProductDto(productEntity1.getModel(), productEntity1.getMaker(), productEntity1.getType());

        MvcResult result = mockMvc.perform(get("/admin/products")
                        .param("model", productEntity1.getModel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ProductDto actual = asObject(responseBody, ProductDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getProduct_whenNotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/products")
                        .param("model", "999999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getJoinedProducts() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/products/fullproduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<ProductJoinedDto> actual = asList(responseBody, ProductJoinedDto.class);

        assertThat(actual)
                .hasSize(2)
                .extracting(ProductJoinedDto::getModel)
                .containsExactlyInAnyOrder(productEntity1.getModel(), productEntity2.getModel());
    }

    @Test
    void getJoinedProducts_whenNoProducts_shouldReturnEmptyList() throws Exception {
        productRepository.deleteAllInBatch();

        MvcResult result = mockMvc.perform(get("/admin/products/fullproduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<ProductJoinedDto> actual = asList(responseBody, ProductJoinedDto.class);

        assertThat(actual).isEmpty();
    }

    @Test
    void insertIntoProduct() throws Exception {
        ProductDto productDto = createProductDto(productEntity1.getModel(), productEntity1.getMaker(), productEntity1.getType());

        MvcResult result = mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(productDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ProductDto actual = asObject(responseBody, ProductDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("model")
                .isEqualTo(productDto);
    }

    @Test
    void patchProductPartially_shouldUpdateExistingProduct() throws Exception {
        ProductDto updateDto = new ProductDto();
        updateDto.setMaker("UpdatedMaker");

        ProductDto expected = createProductDto(productEntity1.getModel(), "UpdatedMaker", productEntity1.getType());

        MvcResult result = mockMvc.perform(patch("/admin/products/{model}", productEntity1.getModel())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ProductDto actual = asObject(responseBody, ProductDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        ProductEntity updatedEntity = productRepository.findById(productEntity1.getModel()).orElse(null);
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedEntity.getMaker()).isEqualTo("UpdatedMaker");
    }

    @Test
    void patchProductPartially_whenProductNotFound_shouldReturn5xx() throws Exception {
        ProductDto updateDto = new ProductDto();
        updateDto.setMaker("UpdatedMaker");

        mockMvc.perform(patch("/admin/products/{model}", "NonExistentModel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(updateDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteFromProduct() throws Exception {
        ProductEntity productEntity = createProductEntity1WithoutId();
        productEntity = productRepository.save(productEntity);

        MvcResult result = mockMvc.perform(delete("/admin/products/{code}", productEntity.getModel()))
                .andExpect(status().isNoContent())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual).isEmpty();
    }

}