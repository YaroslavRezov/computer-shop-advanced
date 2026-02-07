package com.example.computershop.mapper;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.specs.generated.model.LaptopDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.example.computershop.data.LaptopDtoData.createLaptopDto1;
import static com.example.computershop.data.LaptopDtoData.createLaptopDto2;
import static com.example.computershop.data.LaptopEntityData.createLaptopEntity1;
import static com.example.computershop.data.LaptopEntityData.createLaptopEntity2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {LaptopMapper.class})
public class LaptopMapperTest {

    @Autowired
    private LaptopMapper laptopMapper;

    @Test
    void toLaptopDto() {
        LaptopEntity source = createLaptopEntity1();
        LaptopDto expected = createLaptopDto1();

        LaptopDto actual = laptopMapper.toLaptopDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toLaptopDtoList() {
        List<LaptopEntity> source = List.of(createLaptopEntity1(), createLaptopEntity2());
        List<LaptopDto> expected = List.of(createLaptopDto1(), createLaptopDto2());

        List<LaptopDto> actual = laptopMapper.toLaptopDtoList(source);

        assertEquals(expected, actual);
    }
}
