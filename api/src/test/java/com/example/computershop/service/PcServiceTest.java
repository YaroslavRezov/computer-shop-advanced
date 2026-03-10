package com.example.computershop.service;

import com.example.computershop.mapper.PcMapper;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.PcDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static com.example.computershop.data.PcDtoData.createPcDto1;
import static com.example.computershop.data.PcEntityData.createPcEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringJUnitConfig(classes = {PcService.class})
public class PcServiceTest {
    @MockBean
    private PcRepository pcRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private PcMapper pcMapper;

    @Autowired
    private PcService pcService;

    @Test
    public void getPcs() {
        PcEntity pcEntity = new PcEntity();
        PcDto pcDto = new PcDto();
        when(pcRepository.findAll()).thenReturn(List.of(pcEntity));
        when(pcMapper.toPcDtoList(any())).thenReturn(List.of(pcDto));

        List<PcDto> actual = pcService.getPcs();

        verify(pcRepository).findAll();
        verify(pcMapper).toPcDtoList(List.of(pcEntity));
        assertEquals(1, actual.size());
    }

    @Test
    public void getPc() {
        PcEntity pcEntity = createPcEntity1();
        when(pcRepository.findById(any())).thenReturn(Optional.of(pcEntity));
        PcDto expected = createPcDto1();
        when(pcMapper.toPcDto(any())).thenReturn(expected);

        PcDto actual = pcService.getPc(pcEntity.getCode());

        verify(pcRepository).findById(pcEntity.getCode());
        verify(pcMapper).toPcDto(pcEntity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getPc_whenPcNotFound_shouldThrowException() {
        Long code = 80082L;
        when(pcRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pcService.getPc(code));

        verify(pcRepository).findById(code);
        verify(pcMapper, never()).toPcDto(any());
        assertEquals("Нет такого ПК", exception.getMessage());
    }

    @Test
    public void save() {
        ProductEntity productEntity = new ProductEntity();
        PcEntity pcEntity = new PcEntity();
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        PcDto pcDto = new PcDto();
        when(pcMapper.toPcEntity(any(), any())).thenReturn(pcEntity);
        when(pcRepository.save(any())).thenReturn(pcEntity);
        when(pcMapper.toPcDto(any())).thenReturn(pcDto);

        PcDto actual = pcService.save(pcDto);

        verify(productRepository).findById(productEntity.getModel());
        verify(pcMapper).toPcEntity(pcDto, productEntity);
        verify(pcRepository).save(pcEntity);
        verify(pcMapper).toPcDto(pcEntity);
        assertThat(actual).isEqualTo(pcDto);
    }
    @Test
    public void updatePcPartially() {
        PcDto updateDto = new PcDto();
        updateDto.setSpeed(11);
        updateDto.setRam(111);
        updateDto.setHd(1111.0);
        updateDto.setCd("11");
        updateDto.setPrice(1111);

        PcEntity preUpdatedPcEntity = createPcEntity1();

        PcEntity updatedEntity = new PcEntity();
        updatedEntity.setCode(preUpdatedPcEntity.getCode());
        updatedEntity.setSpeed(11);
        updatedEntity.setRam(111);
        updatedEntity.setHd(1111.0);
        updatedEntity.setCd("11");
        updatedEntity.setPrice(1111);

        PcDto expected = createPcDto1();

        when(pcRepository.findById(any())).thenReturn(Optional.of(preUpdatedPcEntity));
        when(pcRepository.save(any())).thenReturn(updatedEntity);
        when(pcMapper.toPcDto(any())).thenReturn(expected);

        PcDto actual = pcService.updatePcPartially(preUpdatedPcEntity.getCode(), updateDto);

        verify(pcRepository).findById(preUpdatedPcEntity.getCode());
        verify(pcRepository).save(preUpdatedPcEntity);
        verify(pcMapper).toPcDto(updatedEntity);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        PcEntity pcEntity = createPcEntity1();
        doNothing().when(pcRepository).deleteById(any());

        pcService.delete(pcEntity.getCode());

        verify(pcRepository).deleteById(pcEntity.getCode());
    }
}
