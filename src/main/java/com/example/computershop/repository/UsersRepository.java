package com.example.computershop.repository;

import com.example.computershop.model.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository <UsersEntity, Long>{
    Optional<UsersEntity> findByUsername(String username);
}
