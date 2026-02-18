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
import static com.example.computershop.data.ProductEntityData.createProductEntity1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(laptopMapper.toLaptopDtoList(List.of(laptopEntity))).thenReturn(List.of(laptopDto));

        List<LaptopEntity> actual = laptopRepository.findAll();

        assertEquals(1, actual.size());
        verify(laptopRepository, times(1)).findAll();
    }

    @Test
    public void getLaptop() {
        LaptopEntity laptopEntity = createLaptopEntity1();
        when(laptopRepository.findById(laptopEntity.getCode())).thenReturn(Optional.of(laptopEntity));
        LaptopDto expected = createLaptopDto1();
        when(laptopMapper.toLaptopDto(laptopEntity)).thenReturn(expected);

        LaptopDto actual = laptopService.getLaptop(laptopEntity.getCode());

        assertThat(actual).isEqualTo(expected);
        verify(laptopRepository).findById(laptopEntity.getCode());
        verify(laptopMapper).toLaptopDto(laptopEntity);
    }

    @Test
    public void save() {
        ProductEntity productEntity = createProductEntity1();
        LaptopEntity laptopEntity = createLaptopEntity1();
        when(productRepository.findById(productEntity.getModel())).thenReturn(Optional.of(productEntity));
        LaptopDto laptopDto = createLaptopDto1();
        when(laptopMapper.toLaptopEntity(laptopDto, productEntity)).thenReturn(laptopEntity);
        when(laptopRepository.save(laptopEntity)).thenReturn(laptopEntity);
        when(laptopMapper.toLaptopDto(laptopEntity)).thenReturn(laptopDto);

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

        when(laptopRepository.findById(preUpdatedLaptopEntity.getCode())).thenReturn(Optional.of(preUpdatedLaptopEntity));
        when(laptopRepository.save(any(LaptopEntity.class))).thenReturn(updatedEntity);
        when(laptopMapper.toLaptopDto(updatedEntity)).thenReturn(expected);

        LaptopDto actual = laptopService.updateLaptopPartially(preUpdatedLaptopEntity.getCode(), updateDto);

        assertThat(actual).isEqualTo(expected);
        verify(laptopRepository).findById(preUpdatedLaptopEntity.getCode());
        verify(laptopRepository).save(preUpdatedLaptopEntity);
        verify(laptopMapper).toLaptopDto(updatedEntity);

        assertThat(preUpdatedLaptopEntity.getSpeed()).isEqualTo(11);
        assertThat(preUpdatedLaptopEntity.getRam()).isEqualTo(111);
        assertThat(preUpdatedLaptopEntity.getHd()).isEqualTo(1111.0);
        assertThat(preUpdatedLaptopEntity.getScreen()).isEqualTo(11);
        assertThat(preUpdatedLaptopEntity.getPrice()).isEqualTo(1111);
    }

    @Test
    void delete() {
        LaptopEntity laptopEntity = createLaptopEntity1();
        doNothing().when(laptopRepository).deleteById(laptopEntity.getCode());

        laptopService.delete(laptopEntity.getCode());

        verify(laptopRepository).deleteById(laptopEntity.getCode());
    }
}
