package com.example.computershop.controller;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.PcDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PcControllerIT extends ControllerIT {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PcRepository pcRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        pcRepository.deleteAll();
        productRepository.deleteAll();

        productEntity = new ProductEntity();
        productEntity.setMaker("TestMaker");
        productEntity.setType("PC");
        productEntity = productRepository.save(productEntity);
    }

    @Test
    void getPcs() throws Exception {
        PcEntity pcEntity = createTestPc(3000, 16, 512.0, "DVD", 1000);

        mockMvc.perform(get("/admin/pcs/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].speed", is(pcEntity.getSpeed())))
                .andExpect(jsonPath("$[0].ram", is(pcEntity.getRam())))
                .andExpect(jsonPath("$[0].hd", is(pcEntity.getHd())))
                .andExpect(jsonPath("$[0].cd", is(pcEntity.getCd())))
                .andExpect(jsonPath("$[0].price", is(pcEntity.getPrice())))
                .andExpect(jsonPath("$[0].model", is(productEntity.getModel())));
    }

    @Test
    void getPc() throws Exception {
        PcEntity pcEntity = createTestPc(3500, 32, 1024.0, "Blu-ray", 1500);

        mockMvc.perform(get("/admin/pcs")
                        .param("code", pcEntity.getCode().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(pcEntity.getCode().intValue())))
                .andExpect(jsonPath("$.speed", is(pcEntity.getSpeed())))
                .andExpect(jsonPath("$.ram", is(pcEntity.getRam())))
                .andExpect(jsonPath("$.hd", is(pcEntity.getHd())))
                .andExpect(jsonPath("$.cd", is(pcEntity.getCd())))
                .andExpect(jsonPath("$.price", is(pcEntity.getPrice())))
                .andExpect(jsonPath("$.model", is(productEntity.getModel())));
    }

    @Test
    void getPc_NotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/pcs")
                        .param("code", "999999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPc() throws Exception {
        PcDto pcDto = createPcDto(productEntity.getModel(), 4000, 64, 2048.0, "DVD-RW", 2000);

        mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pcDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", notNullValue()))
                .andExpect(jsonPath("$.speed", is(pcDto.getSpeed())))
                .andExpect(jsonPath("$.ram", is(pcDto.getRam())))
                .andExpect(jsonPath("$.hd", is(pcDto.getHd())))
                .andExpect(jsonPath("$.cd", is(pcDto.getCd())))
                .andExpect(jsonPath("$.price", is(pcDto.getPrice())))
                .andExpect(jsonPath("$.model", is(productEntity.getModel())));
    }

    @Test
    void insertIntoPc_ProductNotFound_shouldReturn5xx() throws Exception {
        PcDto pcDto = createPcDto("Non-Existent-Model", 4000, 64,
                2048.0, "DVD-RW", 2000);

        mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pcDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPc_MissingRequiredFields_shouldReturn4xx() throws Exception {
        PcDto pcDto = new PcDto();
        pcDto.setModel(productEntity.getModel());

        mockMvc.perform(post("/admin/pcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pcDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void patchPcPartially() throws Exception {
        PcEntity pcEntity = createTestPc(3000, 16, 512.0, "DVD", 1000);
        PcDto updateDto = createPcDto(productEntity.getModel(), 3500, 32, 1024.0, "Blu-ray", 1500);

        mockMvc.perform(patch("/admin/pcs/{code}", pcEntity.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(pcEntity.getCode().intValue())))
                .andExpect(jsonPath("$.speed", is(updateDto.getSpeed())))
                .andExpect(jsonPath("$.ram", is(updateDto.getRam())))
                .andExpect(jsonPath("$.hd", is(updateDto.getHd())))
                .andExpect(jsonPath("$.cd", is(updateDto.getCd())))
                .andExpect(jsonPath("$.price", is(updateDto.getPrice())))
                .andExpect(jsonPath("$.model", is(productEntity.getModel())));
    }

    @Test
    void deleteFromPc() throws Exception {
        PcEntity pcEntity = createTestPc(3000, 16, 512.0, "DVD", 1000);

        mockMvc.perform(delete("/admin/pcs/{code}", pcEntity.getCode()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/admin/pcs/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private PcEntity createTestPc(int speed, int ram, double hd, String cd, int price) {
        PcEntity pc = new PcEntity();
        pc.setProduct(productEntity);
        pc.setSpeed(speed);
        pc.setRam(ram);
        pc.setHd(hd);
        pc.setCd(cd);
        pc.setPrice(price);
        return pcRepository.save(pc);
    }

    private PcDto createPcDto(String model, int speed, int ram, double hd, String cd, int price) {
        PcDto dto = new PcDto();
        dto.setModel(model);
        dto.setSpeed(speed);
        dto.setRam(ram);
        dto.setHd(hd);
        dto.setCd(cd);
        dto.setPrice(price);
        return dto;
    }

}