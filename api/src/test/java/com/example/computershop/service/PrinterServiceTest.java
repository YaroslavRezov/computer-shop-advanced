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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        PrinterEntity printerEntity = createPrinterEntity1();
        PrinterDto printerDto = new PrinterDto();
        when(printerRepository.findAll()).thenReturn(List.of(printerEntity));
        when(printerMapper.toPrinterDtoList(any())).thenReturn(List.of(printerDto));

        List<PrinterDto> actual = printerService.getPrinters();

        verify(printerRepository).findAll();
        verify(printerMapper).toPrinterDtoList(List.of(printerEntity));
        assertEquals(1, actual.size());
    }

    @Test
    public void getPrinter() {
        PrinterEntity printerEntity = createPrinterEntity1();
        when(printerRepository.findById(any())).thenReturn(Optional.of(printerEntity));
        PrinterDto expected = createPrinterDto1();
        when(printerMapper.toPrinterDto(any())).thenReturn(expected);

        PrinterDto actual = printerService.getPrinter(printerEntity.getCode());

        assertThat(actual).isEqualTo(expected);
        verify(printerRepository).findById(printerEntity.getCode());
        verify(printerMapper).toPrinterDto(printerEntity);
    }

    @Test
    public void getPc_whenPcNotFound_shouldThrowException() {
        Long code = 80082L;
        when(printerRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> printerService.getPrinter(code));

        verify(printerRepository).findById(code);
        verify(printerMapper, never()).toPrinterDto(any());
        assertEquals("нет такаого принтера", exception.getMessage());
    }

    @Test
    public void save() {
        ProductEntity productEntity = new ProductEntity();
        PrinterEntity printerEntity = new PrinterEntity();
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        PrinterDto printerDto = new PrinterDto();
        when(printerMapper.toPrinterEntity(any(), any())).thenReturn(printerEntity);
        when(printerRepository.save(any())).thenReturn(printerEntity);
        when(printerMapper.toPrinterDto(any())).thenReturn(printerDto);

        PrinterDto actual = printerService.save(printerDto);

        verify(productRepository).findById(productEntity.getModel());
        verify(printerMapper).toPrinterEntity(printerDto, productEntity);
        verify(printerRepository).save(printerEntity);
        verify(printerMapper).toPrinterDto(printerEntity);
        assertThat(actual).isEqualTo(printerDto);
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

        when(printerRepository.findById(any())).thenReturn(Optional.of(preUpdatedPrinterEntity));
        when(printerRepository.save(any())).thenReturn(updatedEntity);
        when(printerMapper.toPrinterDto(any())).thenReturn(expected);

        PrinterDto actual = printerService.updatePrinterPartially(preUpdatedPrinterEntity.getCode(), updateDto);

        verify(printerRepository).findById(preUpdatedPrinterEntity.getCode());
        verify(printerRepository).save(preUpdatedPrinterEntity);
        verify(printerMapper).toPrinterDto(updatedEntity);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        PrinterEntity printerEntity = createPrinterEntity1();
        doNothing().when(printerRepository).deleteById(any());

        printerService.delete(printerEntity.getCode());

        verify(printerRepository).deleteById(printerEntity.getCode());
    }
}
