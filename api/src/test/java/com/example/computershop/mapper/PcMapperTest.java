package com.example.computershop.mapper;
import com.example.computershop.model.entity.PcEntity;
import com.example.specs.generated.model.PcDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.example.computershop.data.PcDtoData.createPcDto1;
import static com.example.computershop.data.PcDtoData.createPcDto2;
import static com.example.computershop.data.PcEntityData.createPcEntity1;
import static com.example.computershop.data.PcEntityData.createPcEntity2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {PcMapper.class})
public class PcMapperTest {

    @Autowired
    private PcMapper pcMapper;

    @Test
    void toPcDto() {
        PcEntity source = createPcEntity1();
        PcDto expected = createPcDto1();

        PcDto actual = pcMapper.toPcDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toPcDtoList() {
        List<PcEntity> source = List.of(createPcEntity1(), createPcEntity2());
        List<PcDto> expected = List.of(createPcDto1(), createPcDto2());

        List<PcDto> actual = pcMapper.toPcDtoList(source);

        assertEquals(expected, actual);
    }
}
