package com.example.computershop.service;

import com.example.computershop.mapper.PrinterMapper;
import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.PrinterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static com.example.computershop.data.PrinterDtoData.createPrinterDto1;
import static com.example.computershop.data.PrinterEntityData.createPrinterEntity1;
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {PrinterService.class})
public class PrinterServiceTest {

    @MockBean
    private PrinterRepository printerRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private PrinterMapper printerMapper;

    @Autowired
    private PrinterService printerService;

    @Test
    public void getPrinters() {
        PrinterEntity printerEntity = new PrinterEntity();
        PrinterDto printerDto = new PrinterDto();
        when(printerRepository.findAll()).thenReturn(List.of(printerEntity));
        when(printerMapper.toPrinterDtoList(List.of(printerEntity))).thenReturn(List.of(printerDto));

        List<PrinterEntity> actual = printerRepository.findAll();

        assertEquals(1, actual.size());
        verify(printerRepository, times(1)).findAll();
    }

    @Test
    public void getPrinter() {
        PrinterEntity printerEntity = createPrinterEntity1();
        when(printerRepository.findById(printerEntity.getCode())).thenReturn(Optional.of(printerEntity));
        PrinterDto expected = createPrinterDto1();
        when(printerMapper.toPrinterDto(printerEntity)).thenReturn(expected);

        PrinterDto actual = printerService.getPrinter(printerEntity.getCode());

        assertThat(actual).isEqualTo(expected);
        verify(printerRepository).findById(printerEntity.getCode());
        verify(printerMapper).toPrinterDto(printerEntity);
    }

    @Test
    public void save() {
        ProductEntity productEntity = createProductEntity1();
        PrinterEntity printerEntity = createPrinterEntity1();
        when(productRepository.findById(productEntity.getModel())).thenReturn(Optional.of(productEntity));
        PrinterDto printerDto = createPrinterDto1();
        when(printerMapper.toPrinterEntity(printerDto, productEntity)).thenReturn(printerEntity);
        when(printerRepository.save(printerEntity)).thenReturn(printerEntity);
        when(printerMapper.toPrinterDto(printerEntity)).thenReturn(printerDto);

        PrinterDto actual = printerService.save(printerDto);

        assertThat(actual).isEqualTo(printerDto);
        verify(productRepository).findById(productEntity.getModel());
        verify(printerMapper).toPrinterEntity(printerDto, productEntity);
        verify(printerRepository).save(printerEntity);
        verify(printerMapper).toPrinterDto(printerEntity);
    }

    @Test
    public void updatePrinterPartially() {
        PrinterDto updateDto = new PrinterDto();
        updateDto.setColor("y");
        updateDto.setType("lol");
        updateDto.setPrice(1111);

        PrinterEntity preUpdatedPrinterEntity = createPrinterEntity1();

        PrinterEntity updatedEntity = new PrinterEntity();
        updatedEntity.setCode(preUpdatedPrinterEntity.getCode());
        updatedEntity.setColor("y");
        updatedEntity.setType("lol");
        updatedEntity.setPrice(1111);

        PrinterDto expected = createPrinterDto1();

        when(printerRepository.findById(preUpdatedPrinterEntity.getCode())).thenReturn(Optional.of(preUpdatedPrinterEntity));
        when(printerRepository.save(any(PrinterEntity.class))).thenReturn(updatedEntity);
        when(printerMapper.toPrinterDto(updatedEntity)).thenReturn(expected);

        PrinterDto actual = printerService.updatePrinterPartially(preUpdatedPrinterEntity.getCode(), updateDto);

        assertThat(actual).isEqualTo(expected);
        verify(printerRepository).findById(preUpdatedPrinterEntity.getCode());
        verify(printerRepository).save(preUpdatedPrinterEntity);
        verify(printerMapper).toPrinterDto(updatedEntity);

        assertThat(preUpdatedPrinterEntity.getType()).isEqualTo("lol");
        assertThat(preUpdatedPrinterEntity.getColor()).isEqualTo("y");
        assertThat(preUpdatedPrinterEntity.getPrice()).isEqualTo(1111);
    }

    @Test
    void delete() {
        PrinterEntity printerEntity = createPrinterEntity1();
        doNothing().when(printerRepository).deleteById(printerEntity.getCode());

        printerService.delete(printerEntity.getCode());

        verify(printerRepository).deleteById(printerEntity.getCode());
    }
}
