package com.example.computershop.repository;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDeviceProductRepository extends JpaRepository<CartDeviceProductEntity, Long> {

}
