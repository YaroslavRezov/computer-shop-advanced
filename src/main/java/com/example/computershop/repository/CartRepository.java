package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
}
