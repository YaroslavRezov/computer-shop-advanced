package com.example.computershop.controller;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.utils.TestUtils;
import com.example.specs.generated.model.LaptopDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.example.computershop.data.LaptopDtoData.createLaptopDto;
import static com.example.computershop.data.LaptopEntityData.createLaptopEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LaptopControllerIT extends ControllerIT {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LaptopRepository laptopRepository;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        laptopRepository.deleteAll();
        productRepository.deleteAll();

        productEntity = new ProductEntity();
        productEntity.setMaker("TestMaker");
        productEntity.setType("Laptop");
        productEntity = productRepository.save(productEntity);
    }

    @Test
    void getLaptops() throws Exception {
        LaptopEntity laptopEntity = createLaptopEntity(productEntity, 3000, 16, 512.0, 1000, 12);
        laptopEntity = laptopRepository.save(laptopEntity);
        LaptopDto expected = createLaptopDto(productEntity.getModel(), 3000, 16, 512.0, 1000, 12);
        expected.setCode(laptopEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/laptops/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<LaptopDto> actual = TestUtils.asList(responseBody, LaptopDto.class);

        assertThat(actual)
                .hasSize(1)
                .usingRecursiveComparison()
                .isEqualTo(List.of(expected));
    }

    @Test
    void getLaptop() throws Exception {
        LaptopEntity laptopEntity = createLaptopEntity(productEntity, 3000, 16, 512.0, 1000, 12);
        laptopEntity = laptopRepository.save(laptopEntity);
        LaptopDto expected = createLaptopDto(productEntity.getModel(), 3000, 16, 512.0, 1000, 12);
        expected.setCode(laptopEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/laptops")
                        .param("code", laptopEntity.getCode().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LaptopDto actual = TestUtils.asObject(responseBody, LaptopDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getLaptop_whenNotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/laptops")
                        .param("code", "999999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoLaptop() throws Exception {
        LaptopDto laptopDto = createLaptopDto(productEntity.getModel(), 3000, 16, 512.0, 1000, 12);

        MvcResult result = mockMvc.perform(post("/admin/laptops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(laptopDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LaptopDto actual = TestUtils.asObject(responseBody, LaptopDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("code")
                .isEqualTo(laptopDto);
    }

    @Test
    void insertIntoLaptop_whenProductNotFound_shouldReturn5xx() throws Exception {
        LaptopDto laptopDto = createLaptopDto("Non-Existent-Model", 3000, 16, 512.0, 1000, 12);

        mockMvc.perform(post("/admin/laptops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(laptopDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoLaptop_whenMissingRequiredFields_shouldReturn4xx() throws Exception {
        LaptopDto laptopDto = new LaptopDto();
        laptopDto.setModel(productEntity.getModel());

        mockMvc.perform(post("/admin/laptops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(laptopDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void patchLaptopPartially() throws Exception {
        LaptopEntity laptopEntity = createLaptopEntity(productEntity, 3000, 16, 512.0, 1000, 12);
        laptopEntity = laptopRepository.save(laptopEntity);
        LaptopDto updateDto = createLaptopDto(productEntity.getModel(),3000, 16, 512.0, 1000, 12);
        LaptopDto expected = updateDto.code(laptopEntity.getCode());

        MvcResult result = mockMvc.perform(patch("/admin/laptops/{code}", laptopEntity.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LaptopDto actual = TestUtils.asObject(responseBody, LaptopDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromLaptop() throws Exception {
        LaptopEntity laptopEntity = createLaptopEntity(productEntity, 3000, 16, 512.0, 1000, 12);
        laptopEntity = laptopRepository.save(laptopEntity);

        MvcResult result = mockMvc.perform(delete("/admin/laptops/{code}", laptopEntity.getCode()))
                .andExpect(status().isNoContent())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual).isEmpty();
    }

}