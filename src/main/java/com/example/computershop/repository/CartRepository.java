package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
    List<CartEntity> findByUserId(String userId);
}
