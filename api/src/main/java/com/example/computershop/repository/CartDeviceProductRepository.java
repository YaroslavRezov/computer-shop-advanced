package com.example.computershop.repository;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDeviceProductRepository extends JpaRepository<CartDeviceProductEntity, Long> {
    List<CartDeviceProductEntity> findByCartEntity(CartEntity cart);
    void deleteByCartEntity(CartEntity cart);
    void deleteByCartEntityCartId(Long cartId);
}
