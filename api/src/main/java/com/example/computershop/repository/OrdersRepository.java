package com.example.computershop.repository;

import com.example.computershop.model.entity.OrdersEntity;
import com.example.computershop.model.entity.OrdersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    @Query("""
    SELECT
        o.orderId AS orderId,
        cdp.code AS code,
        p.model AS model,
        o.amount AS amount,
        o.status AS status,
        u.username AS username,
        u.email AS email
    FROM OrdersEntity o
    JOIN o.cartEntity c
    JOIN c.user u
    JOIN c.devices cdp
    JOIN cdp.productEntity p
    WHERE u.username = :username
    """)
    List<OrdersView> findByUsername(
            @Param("username") String username);
}
