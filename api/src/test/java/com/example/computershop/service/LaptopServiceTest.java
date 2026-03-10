package com.example.computershop.service;

import com.example.computershop.mapper.LaptopMapper;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.LaptopDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static com.example.computershop.data.LaptopDtoData.createLaptopDto1;
import static com.example.computershop.data.LaptopEntityData.createLaptopEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {LaptopService.class})
public class LaptopServiceTest {

    @MockBean
    private LaptopRepository laptopRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private LaptopMapper laptopMapper;

    @Autowired
    private LaptopService laptopService;

    @Test
    public void getLaptops() {
        LaptopEntity laptopEntity = new LaptopEntity();
        LaptopDto laptopDto = new LaptopDto();
        when(laptopRepository.findAll()).thenReturn(List.of(laptopEntity));
        when(laptopMapper.toLaptopDtoList(any())).thenReturn(List.of(laptopDto));

        List<LaptopDto> actual = laptopService.getLaptops();

        assertEquals(1, actual.size());
        verify(laptopRepository).findAll();
        verify(laptopMapper).toLaptopDtoList(List.of(laptopEntity));
    }

    @Test
    public void getLaptop() {
        LaptopEntity laptopEntity = createLaptopEntity1();
        when(laptopRepository.findById(any())).thenReturn(Optional.of(laptopEntity));
        LaptopDto expected = createLaptopDto1();
        when(laptopMapper.toLaptopDto(any())).thenReturn(expected);

        LaptopDto actual = laptopService.getLaptop(laptopEntity.getCode());

        verify(laptopRepository).findById(laptopEntity.getCode());
        verify(laptopMapper).toLaptopDto(laptopEntity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getLaptop_whenLaptopNotFound_shouldThrowException() {
        Long code = 80082L;
        when(laptopRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> laptopService.getLaptop(code));

        verify(laptopRepository).findById(code);
        verify(laptopMapper, never()).toLaptopDto(any());
        assertEquals("Нет такого ноута", exception.getMessage());
    }

    @Test
    public void save() {
        ProductEntity productEntity = new ProductEntity();
        LaptopEntity laptopEntity = new LaptopEntity();
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        LaptopDto laptopDto = new LaptopDto();
        when(laptopMapper.toLaptopEntity(any(), any())).thenReturn(laptopEntity);
        when(laptopRepository.save(any())).thenReturn(laptopEntity);
        when(laptopMapper.toLaptopDto(any())).thenReturn(laptopDto);

        LaptopDto actual = laptopService.save(laptopDto);

        assertThat(actual).isEqualTo(laptopDto);
        verify(productRepository).findById(productEntity.getModel());
        verify(laptopMapper).toLaptopEntity(laptopDto, productEntity);
        verify(laptopRepository).save(laptopEntity);
        verify(laptopMapper).toLaptopDto(laptopEntity);
    }

    @Test
    public void updateLaptopPartially() {
        LaptopDto updateDto = new LaptopDto();
        updateDto.setSpeed(11);
        updateDto.setRam(111);
        updateDto.setHd(1111.0);
        updateDto.setScreen(11);
        updateDto.setPrice(1111);

        LaptopEntity preUpdatedLaptopEntity = createLaptopEntity1();

        LaptopEntity updatedEntity = new LaptopEntity();
        updatedEntity.setCode(preUpdatedLaptopEntity.getCode());
        updatedEntity.setSpeed(11);
        updatedEntity.setRam(111);
        updatedEntity.setHd(1111.0);
        updatedEntity.setScreen(11);
        updatedEntity.setPrice(1111);

        LaptopDto expected = createLaptopDto1();

        when(laptopRepository.findById(any())).thenReturn(Optional.of(preUpdatedLaptopEntity));
        when(laptopRepository.save(any())).thenReturn(updatedEntity);
        when(laptopMapper.toLaptopDto(any())).thenReturn(expected);

        LaptopDto actual = laptopService.updateLaptopPartially(preUpdatedLaptopEntity.getCode(), updateDto);

        verify(laptopRepository).findById(preUpdatedLaptopEntity.getCode());
        verify(laptopRepository).save(preUpdatedLaptopEntity);
        verify(laptopMapper).toLaptopDto(updatedEntity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        LaptopEntity laptopEntity = createLaptopEntity1();
        doNothing().when(laptopRepository).deleteById(laptopEntity.getCode());

        laptopService.delete(laptopEntity.getCode());

        verify(laptopRepository).deleteById(laptopEntity.getCode());
    }
}
