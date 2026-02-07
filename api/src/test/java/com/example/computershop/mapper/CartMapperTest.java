package com.example.computershop.mapper;

import com.example.computershop.model.entity.CartEntity;
import com.example.specs.generated.model.CartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.example.computershop.data.CartDtoData.createCartDto1;
import static com.example.computershop.data.CartDtoData.createCartDto2;
import static com.example.computershop.data.CartEntityData.createCartEntity1;
import static com.example.computershop.data.CartEntityData.createCartEntity2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {CartMapper.class})
public class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    void toCartDto() {
        CartEntity source = createCartEntity1();
        CartDto expected = createCartDto1();

        CartDto actual = cartMapper.toCartDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toCartDtoList() {
        List<CartEntity> source = List.of(createCartEntity1(), createCartEntity2());
        List<CartDto> expected = List.of(createCartDto1(), createCartDto2());

        List<CartDto> actual = cartMapper.toCartDtoList(source);

        assertEquals(expected, actual);
    }
}
