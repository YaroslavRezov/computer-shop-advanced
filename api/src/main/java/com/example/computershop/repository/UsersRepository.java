package com.example.computershop.repository;

import com.example.computershop.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByUsername(String username);
}
