package com.example.computershop.repository;

import com.example.computershop.model.entity.PcEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcRepository extends JpaRepository<PcEntity, Long> {

}
