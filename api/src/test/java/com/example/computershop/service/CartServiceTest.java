package com.example.computershop.service;

import com.example.computershop.mapper.CartMapper;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.repository.CartRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.repository.UsersRepository;
import com.example.specs.generated.model.CartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.computershop.data.CartDtoData.createCartDto1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {CartService.class})
class CartServiceTest {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private CartMapper cartMapper;

    @Autowired
    private CartService cartService;

    @Test
    void getAll() {
        CartEntity cartEntity = new CartEntity();
        CartDto cartDto = new CartDto();
        when(cartRepository.findAll()).thenReturn(List.of(cartEntity));
        when(cartMapper.toCartDtoList(any())).thenReturn(List.of(cartDto));

        List<CartDto> actual = cartService.getAll();

        verify(cartRepository).findAll();
        verify(cartMapper).toCartDtoList(List.of(cartEntity));
        assertEquals(1, actual.size());
    }

    @Test
    void getCartForUser() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        CartEntity cartEntity = new CartEntity();
        CartDto cartDto = new CartDto();
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(List.of(cartEntity));
        when(cartMapper.toCartDtoList(any())).thenReturn(List.of(cartDto));

        List<CartDto> actual = cartService.getCartForUser(username);

        verify(usersRepository).findByUsername(username);
        verify(cartRepository).findByUser(user);
        verify(cartMapper).toCartDtoList(List.of(cartEntity));
        assertEquals(1, actual.size());
    }

    @Test
    void getCartForUser_whenInvalidUser_shouldThrowException() {
        String username = "unknown";
        when(usersRepository.findByUsername(any())).thenReturn(Optional.empty());

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.getCartForUser(username));

        verify(usersRepository).findByUsername(username);
        verify(cartRepository, never()).findByUser(any());
        verify(cartMapper, never()).toCartDtoList(any());
        assertEquals("Нет такого пользователя" + username, actual.getMessage());
    }

    @Test
    void getCartForUser_whenEmptyCart_shouldReturnEmptyList() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Collections.emptyList());
        when(cartMapper.toCartDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<CartDto> actual = cartService.getCartForUser(username);

        verify(usersRepository).findByUsername(username);
        verify(cartRepository).findByUser(user);
        verify(cartMapper).toCartDtoList(Collections.emptyList());
        assertTrue(actual.isEmpty());
    }

    @Test
    void save() {
        ProductEntity product = new ProductEntity();
        UsersEntity user = new UsersEntity();
        CartEntity savedEntity = new CartEntity();
        CartDto responseDto = new CartDto();
        when(productService.getPriceByCode(any())).thenReturn(Optional.of(600));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.save(any())).thenReturn(savedEntity);
        when(cartMapper.toCartDto(any())).thenReturn(responseDto);

        CartDto actual = cartService.save(createCartDto1());

        verify(productService).getPriceByCode(1L);
        verify(productRepository).findById("1276");
        verify(usersRepository).findByUsername("Omen");
        verify(cartRepository).save(any());
        verify(cartMapper).toCartDto(savedEntity);
        assertNotNull(actual);
    }

    @Test
    void save_whenProductNotFound_shouldThrowException() {
        CartDto requestDto = new CartDto();
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        when(productService.getPriceByCode(any())).thenReturn(Optional.of(600));

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.save(requestDto));

        verify(productService).getPriceByCode(any());
        verify(productRepository).findById(any());
        verify(usersRepository, never()).findByUsername(any());
        verify(cartRepository, never()).save(any());
        verify(cartMapper, never()).toCartDto(any());
        assertEquals("Нет такого продукта", actual.getMessage());
    }

    @Test
    void delete_whenUsername() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        doNothing().when(cartRepository).deleteByUser(any());

        cartService.delete(username);

        verify(cartRepository).deleteByUser(user);
        verify(usersRepository).findByUsername(username);
    }

    @Test
    void delete_whenOrderId() {
        Long orderId = 103L;
        doNothing().when(cartRepository).deleteById(orderId);

        cartService.delete(orderId);

        verify(cartRepository).deleteById(orderId);
    }
}