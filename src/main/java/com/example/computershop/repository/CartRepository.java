package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
    List<CartEntity> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartEntity c WHERE c.username = :username")
    void deleteByUsername(@Param("username") String username);
}
