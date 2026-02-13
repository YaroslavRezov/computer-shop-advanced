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

import java.util.*;

import static com.example.computershop.data.CartDtoData.createCartDto1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(cartMapper.toCartDtoList(List.of(cartEntity))).thenReturn(List.of(cartDto));

        List<CartDto> actual = cartService.getAll();

        assertEquals(1, actual.size());
        verify(cartRepository, times(1)).findAll();
    }

    @Test
    void getCartForUser() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        CartEntity cartEntity = new CartEntity();
        CartDto cartDto = new CartDto();
        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(List.of(cartEntity));
        when(cartMapper.toCartDtoList(List.of(cartEntity))).thenReturn(List.of(cartDto));

        List<CartDto> actual = cartService.getCartForUser(username);

        assertEquals(1, actual.size());
        verify(usersRepository, times(1)).findByUsername(username);
    }

    @Test
    void getCartForUser_whenInvalidUser_shouldThrowException() {
        String username = "unknown";
        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.getCartForUser(username));
        
        assertTrue(actual.getMessage().contains(username));
    }

    @Test
    void save() {
        ProductEntity product = new ProductEntity();
        UsersEntity user = new UsersEntity();
        CartEntity savedEntity = new CartEntity();
        CartDto responseDto = new CartDto();
        when(productService.getPriceByCode(1L)).thenReturn(Optional.of(600));
        when(productRepository.findById("1276")).thenReturn(Optional.of(product));
        when(usersRepository.findByUsername("Omen")).thenReturn(Optional.of(user));
        when(cartRepository.save(any())).thenReturn(savedEntity);
        when(cartMapper.toCartDto(savedEntity)).thenReturn(responseDto);

        CartDto actual = cartService.save(createCartDto1());

        assertNotNull(actual);
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    void save_whenProductNotFound_shouldThrowException() {
        CartDto requestDto = new CartDto().model("invalid").code(999L);
        when(productService.getPriceByCode(999L)).thenReturn(Optional.of(100));
        when(productRepository.findById("invalid")).thenReturn(Optional.empty());

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.save(requestDto));

        assertTrue(actual.getMessage().contains("продукта"));
    }

    @Test
    void delete_whenUsername() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(cartRepository).deleteByUser(user);

        cartService.delete(username);

        verify(cartRepository, times(1)).deleteByUser(user);
    }

    @Test
    void delete_whenOrderId() {
        Long orderId = 103L;
        doNothing().when(cartRepository).deleteById(orderId);

        cartService.delete(orderId);

        verify(cartRepository, times(1)).deleteById(orderId);
    }

    @Test
    void getCartForUser_whenEmptyCart_shouldReturnEmptyList() {
        String username = "Omen";
        UsersEntity user = new UsersEntity();
        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Collections.emptyList());
        when(cartMapper.toCartDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<CartDto> actual = cartService.getCartForUser(username);

        assertTrue(actual.isEmpty());
    }
}