package com.example.computershop.repository;

import com.example.computershop.model.entity.LaptopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface LaptopRepository extends JpaRepository<LaptopEntity, Long> {

}
