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
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(pcMapper.toPcDtoList(List.of(pcEntity))).thenReturn(List.of(pcDto));

        List<PcEntity> actual = pcRepository.findAll();

        assertEquals(1, actual.size());
        verify(pcRepository, times(1)).findAll();
    }

    @Test
    public void getPc() {
        PcEntity pcEntity = createPcEntity1();
        when(pcRepository.findById(pcEntity.getCode())).thenReturn(Optional.of(pcEntity));
        PcDto expected = createPcDto1();
        when(pcMapper.toPcDto(pcEntity)).thenReturn(expected);

        PcDto actual = pcService.getPc(pcEntity.getCode());

        assertThat(actual).isEqualTo(expected);
        verify(pcRepository).findById(pcEntity.getCode());
        verify(pcMapper).toPcDto(pcEntity);
    }

    @Test
    public void save() {
        ProductEntity productEntity = createProductEntity1();
        PcEntity pcEntity = createPcEntity1();
        when(productRepository.findById(productEntity.getModel())).thenReturn(Optional.of(productEntity));
        PcDto pcDto = createPcDto1();
        when(pcMapper.toPcEntity(pcDto, productEntity)).thenReturn(pcEntity);
        when(pcRepository.save(pcEntity)).thenReturn(pcEntity);
        when(pcMapper.toPcDto(pcEntity)).thenReturn(pcDto);

        PcDto actual = pcService.save(pcDto);

        assertThat(actual).isEqualTo(pcDto);
        verify(productRepository).findById(productEntity.getModel());
        verify(pcMapper).toPcEntity(pcDto, productEntity);
        verify(pcRepository).save(pcEntity);
        verify(pcMapper).toPcDto(pcEntity);
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

        when(pcRepository.findById(preUpdatedPcEntity.getCode())).thenReturn(Optional.of(preUpdatedPcEntity));
        when(pcRepository.save(any(PcEntity.class))).thenReturn(updatedEntity);
        when(pcMapper.toPcDto(updatedEntity)).thenReturn(expected);

        PcDto actual = pcService.updatePcPartially(preUpdatedPcEntity.getCode(), updateDto);

        assertThat(actual).isEqualTo(expected);
        verify(pcRepository).findById(preUpdatedPcEntity.getCode());
        verify(pcRepository).save(preUpdatedPcEntity);
        verify(pcMapper).toPcDto(updatedEntity);

        assertThat(preUpdatedPcEntity.getSpeed()).isEqualTo(11);
        assertThat(preUpdatedPcEntity.getRam()).isEqualTo(111);
        assertThat(preUpdatedPcEntity.getHd()).isEqualTo(1111.0);
        assertThat(preUpdatedPcEntity.getCd()).isEqualTo("11");
        assertThat(preUpdatedPcEntity.getPrice()).isEqualTo(1111);
    }

    @Test
    void delete() {
        PcEntity pcEntity = createPcEntity1();
        doNothing().when(pcRepository).deleteById(pcEntity.getCode());

        pcService.delete(pcEntity.getCode());

        verify(pcRepository).deleteById(pcEntity.getCode());
    }
}
