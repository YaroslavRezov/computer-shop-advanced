package com.example.computershop.controller;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.utils.TestUtils;
import com.example.specs.generated.model.PcDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static com.example.computershop.data.PcDtoData.createPcDto;
import static com.example.computershop.data.PcEntityData.createPcEntity;
import static com.example.computershop.data.ProductEntityData.createProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PcControllerIT extends ControllerIT {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PcRepository pcRepository;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productEntity = createProductEntity("TestMaker", "PC");
        productEntity = productRepository.save(productEntity);
    }

    @AfterEach
    void cleanUp() {
        pcRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    void getPcs() throws Exception {
        PcEntity pcEntity = createPcEntity(productEntity, 3000, 16, 512.0, "DVD", 1000);
        pcEntity = pcRepository.save(pcEntity);
        PcDto expected = createPcDto(productEntity.getModel(), 3000, 16, 512.0, "DVD", 1000);
        expected.setCode(pcEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/pcs/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<PcDto> actual = TestUtils.asList(responseBody, PcDto.class);

        assertThat(actual)
                .hasSize(1)
                .usingRecursiveComparison()
                .isEqualTo(List.of(expected));
    }

    @Test
    void getPc() throws Exception {
        PcEntity pcEntity = createPcEntity(productEntity, 3500, 32, 1024.0, "Blu-ray", 1500);
        pcEntity = pcRepository.save(pcEntity);
        PcDto expected = createPcDto(productEntity.getModel(), 3500, 32, 1024.0, "Blu-ray", 1500);
        expected.setCode(pcEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/pcs")
                        .param("code", pcEntity.getCode().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PcDto actual = TestUtils.asObject(responseBody, PcDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getPc_whenNotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/pcs")
                        .param("code", "999999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPc() throws Exception {
        PcDto pcDto = createPcDto(productEntity.getModel(), 4000, 64, 2048.0, "DVD-RW", 2000);

        MvcResult result = mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(pcDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PcDto actual = TestUtils.asObject(responseBody, PcDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("code")
                .isEqualTo(pcDto);
    }

    @Test
    void insertIntoPc_whenProductNotFound_shouldReturn5xx() throws Exception {
        PcDto pcDto = createPcDto("Non-Existent-Model", 4000, 64, 2048.0, "DVD-RW", 2000);

        mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(pcDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPc_whenMissingRequiredFields_shouldReturn4xx() throws Exception {
        PcDto pcDto = new PcDto();
        pcDto.setModel(productEntity.getModel());

        mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(pcDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void patchPcPartially() throws Exception {
        PcEntity pcEntity = createPcEntity(productEntity, 3000, 16, 512.0, "DVD", 1000);
        pcEntity = pcRepository.save(pcEntity);
        PcDto updateDto = createPcDto(productEntity.getModel(), 3500, 32, 1024.0, "Blu-ray", 1500);
        PcDto expected = updateDto.code(pcEntity.getCode());

        MvcResult result = mockMvc.perform(patch("/admin/pcs/{code}", pcEntity.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PcDto actual = TestUtils.asObject(responseBody, PcDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromPc() throws Exception {
        PcEntity pcEntity = createPcEntity(productEntity, 3000, 16, 512.0, "DVD", 1000);
        pcEntity = pcRepository.save(pcEntity);

        MvcResult result = mockMvc.perform(delete("/admin/pcs/{code}", pcEntity.getCode()))
                .andExpect(status().isNoContent())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual).isEmpty();
    }

}