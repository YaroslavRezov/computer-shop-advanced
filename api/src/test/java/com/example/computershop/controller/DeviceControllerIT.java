package com.example.computershop.controller;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.DeviceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.example.computershop.data.LaptopEntityData.createLaptopEntity1;
import static com.example.computershop.data.PcEntityData.createPcEntity1;
import static com.example.computershop.data.PrinterEntityData.createPrinterEntity1;
import static com.example.computershop.data.ProductEntityData.createProductEntity;
import static com.example.computershop.utils.TestUtils.asList;
import static com.example.computershop.utils.TestUtils.asObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeviceControllerIT extends ControllerIT {

    @Autowired
    private PcRepository pcRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private ProductRepository productRepository;

    private ProductEntity pcProduct;
    private ProductEntity laptopProduct;
    private ProductEntity printerProduct;
    private PcEntity pcEntity;
    private LaptopEntity laptopEntity;
    private PrinterEntity printerEntity;

    @BeforeEach
    void setUp() {
        pcRepository.deleteAll();
        productRepository.deleteAll();

        pcProduct = createProductEntity("A", "PC");
        pcProduct = productRepository.save(pcProduct);
        laptopProduct = createProductEntity("A", "Laptop");
        laptopProduct = productRepository.save(laptopProduct);
        printerProduct = createProductEntity("A", "Printer");
        printerProduct = productRepository.save(printerProduct);

        pcEntity = createPcEntity1();
        pcEntity.setProduct(pcProduct);
        pcEntity = pcRepository.save(pcEntity);
        laptopEntity = createLaptopEntity1();
        laptopEntity.setProduct(laptopProduct);
        laptopEntity = laptopRepository.save(laptopEntity);
        printerEntity = createPrinterEntity1();
        printerEntity.setProduct(printerProduct);
        printerEntity = printerRepository.save(printerEntity);
    }


    @Test
    void getDevices() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/devices/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<DeviceDto> actual = asList(responseBody, DeviceDto.class);

        assertThat(actual)
                .hasSize(3)
                .extracting(DeviceDto::getModel)
                .containsExactlyInAnyOrder(
                        pcEntity.getProduct().getModel(),
                        laptopEntity.getProduct().getModel(),
                        printerEntity.getProduct().getModel()
                );
    }

    @Test
    void getDevices_whenNoDevices_shouldReturnEmptyList() throws Exception {
        pcRepository.deleteAll();
        laptopRepository.deleteAll();
        printerRepository.deleteAll();

        MvcResult result = mockMvc.perform(get("/admin/devices/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<DeviceDto> actual = asList(responseBody, DeviceDto.class);

        assertThat(actual).isEmpty();
    }

    @Test
    void getDevice_shouldReturnPcDevice() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/devices")
                        .param("model", pcProduct.getModel()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DeviceDto actual = asObject(responseBody, DeviceDto.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getModel()).isEqualTo(pcProduct.getModel());
        assertThat(actual.getPrice()).isEqualTo(600);
    }

    @Test
    void getDevice_whenNotFound_shouldReturn5xx() throws Exception {
        mockMvc.perform(get("/admin/devices")
                        .param("model", "999999"))
                .andExpect(status().is5xxServerError());
    }

}