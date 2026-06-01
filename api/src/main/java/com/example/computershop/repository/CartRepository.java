package com.example.computershop.repository;

import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUser(UsersEntity user);// Было findByUsername

    Optional<CartEntity> findByUserId(UsersEntity user);
    @Modifying
    @Transactional
    @Query("DELETE FROM CartEntity c WHERE c.user = :user")
    void deleteByUser(@Param("user") UsersEntity user); // Было deleteByUsername
}