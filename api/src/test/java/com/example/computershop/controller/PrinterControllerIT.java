package com.example.computershop.controller;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.utils.TestUtils;
import com.example.specs.generated.model.PrinterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.example.computershop.data.PrinterDtoData.createPrinterDto;
import static com.example.computershop.data.PrinterEntityData.createPrinterEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PrinterControllerIT extends ControllerIT {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PrinterRepository printerRepository;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        printerRepository.deleteAll();
        productRepository.deleteAll();

        productEntity = new ProductEntity();
        productEntity.setMaker("TestMaker");
        productEntity.setType("Printer");
        productEntity = productRepository.save(productEntity);
    }

    @Test
    void getPrinters() throws Exception {
        PrinterEntity printerEntity = createPrinterEntity(productEntity, "y", "Jet", 1000);
        printerEntity = printerRepository.save(printerEntity);
        PrinterDto expected = createPrinterDto(productEntity.getModel(), "Цветной", "Jet", 1000);
        expected.setCode(printerEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/printers/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<PrinterDto> actual = TestUtils.asList(responseBody, PrinterDto.class);

        assertThat(actual)
                .hasSize(1)
                .usingRecursiveComparison()
                .ignoringFields("color")
                .isEqualTo(List.of(expected));
    }

    @Test
    void getPrinter() throws Exception {
        PrinterEntity printerEntity = createPrinterEntity(productEntity, "y", "Jet", 1000);
        printerEntity = printerRepository.save(printerEntity);
        PrinterDto expected = createPrinterDto(productEntity.getModel(), "Цветной", "Jet", 1000);
        expected.setCode(printerEntity.getCode());

        MvcResult result = mockMvc.perform(get("/admin/printers")
                        .param("code", printerEntity.getCode().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PrinterDto actual = TestUtils.asObject(responseBody, PrinterDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("color")
                .isEqualTo(expected);
    }

    @Test
    void getPrinter_whenNotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/printers")
                        .param("code", "999999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPrinter() throws Exception {
        PrinterDto printerDto = createPrinterDto(productEntity.getModel(), "Цветной", "Jet", 1000);

        MvcResult result = mockMvc.perform(post("/admin/printers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(printerDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PrinterDto actual = TestUtils.asObject(responseBody, PrinterDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("code")
                .ignoringFields("color")
                .isEqualTo(printerDto);
    }

    @Test
    void insertIntoPrinter_whenProductNotFound_shouldReturn5xx() throws Exception {
        PrinterDto printerDto = createPrinterDto("Non-Existent-Model", "Цветной", "lol", 1000000);

        mockMvc.perform(post("/admin/printers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(printerDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void insertIntoPrinter_whenMissingRequiredFields_shouldReturn4xx() throws Exception {
        PrinterDto printerDto = new PrinterDto();
        printerDto.setModel(productEntity.getModel());

        mockMvc.perform(post("/admin/printers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(printerDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void patchPrinterPartially() throws Exception {
        PrinterEntity printerEntity = createPrinterEntity(productEntity, "y", "Jet", 1000);
        printerEntity = printerRepository.save(printerEntity);
        PrinterDto updateDto = createPrinterDto(productEntity.getModel(), "Цветной", "Jet", 1500);
        PrinterDto expected = updateDto.code(printerEntity.getCode());

        MvcResult result = mockMvc.perform(patch("/admin/printers/{code}", printerEntity.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PrinterDto actual = TestUtils.asObject(responseBody, PrinterDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("color")
                .isEqualTo(expected);
    }

    @Test
    void deleteFromPrinter() throws Exception {
        PrinterEntity printerEntity = createPrinterEntity(productEntity, "y", "Jet", 1000);
        printerEntity = printerRepository.save(printerEntity);

        MvcResult result = mockMvc.perform(delete("/admin/printers/{code}", printerEntity.getCode()))
                .andExpect(status().isNoContent())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual).isEmpty();
    }

}