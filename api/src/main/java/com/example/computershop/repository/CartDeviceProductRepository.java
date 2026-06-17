package com.example.computershop.repository;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDeviceProductRepository extends JpaRepository<CartDeviceProductEntity, Long> {
    List<CartDeviceProductEntity> findByCartEntityIn(List<CartEntity> carts);
    void deleteByCartEntityIn(List<CartEntity> carts);
    void deleteByCartEntityCartId(Long cartId);
}
