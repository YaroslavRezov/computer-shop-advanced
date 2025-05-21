package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
    List<CartEntity> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartEntity c WHERE c.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
