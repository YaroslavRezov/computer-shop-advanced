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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

    @MockitoBean
    private CartRepository cartRepository;
    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private ProductRepository productRepository;
    @MockitoBean
    private UsersRepository usersRepository;
    @MockitoBean
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
        CartDto cartDto1 = createCartDto1();
        ProductEntity product = new ProductEntity();
        UsersEntity user = new UsersEntity();
        CartEntity cart = new CartEntity();
        cart.setCode(cartDto1.getCode());
        cart.setProduct(product);
        cart.setUser(user);
        cart.setPrice(600);
        when(productService.getPriceByCode(any())).thenReturn(Optional.of(600));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.save(any())).thenReturn(cart);
        when(cartMapper.toCartDto(any())).thenReturn(new CartDto());

        CartDto actual = cartService.save(cartDto1);

        verify(productService).getPriceByCode(cartDto1.getCode());
        verify(productRepository).findById(cartDto1.getModel());
        verify(usersRepository).findByUsername(cartDto1.getUsername());
        verify(cartRepository).save(cart);
        verify(cartMapper).toCartDto(cart);
        assertNotNull(actual);
    }

    @Test
    void save_whenProductNotFound_shouldThrowException() {
        CartDto cartDto = new CartDto().model("invalid").code(999L);
        when(productService.getPriceByCode(any())).thenReturn(Optional.of(100));
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.save(cartDto));

        verify(productService).getPriceByCode(cartDto.getCode());
        verify(productRepository).findById(cartDto.getModel());
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

        verify(usersRepository).findByUsername(username);
        verify(cartRepository).deleteByUser(user);
    }

    @Test
    void delete_whenUserNotFound_shouldThrowException() {
        String username = "Omen";
        when(usersRepository.findByUsername(any())).thenReturn(Optional.empty());

        RuntimeException actual = assertThrows(RuntimeException.class, () -> cartService.delete(username));

        verify(usersRepository).findByUsername(username);
        verify(cartRepository, never()).deleteByUser(any());
        assertEquals("Нет такого пользователя", actual.getMessage());
    }

    @Test
    void delete_whenOrderId() {
        Long orderId = 103L;
        doNothing().when(cartRepository).deleteById(any());

        cartService.delete(orderId);

        verify(cartRepository).deleteById(orderId);
    }

}