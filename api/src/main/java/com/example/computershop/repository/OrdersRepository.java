package com.example.computershop.repository;

import com.example.computershop.model.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
}
