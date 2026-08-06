package com.example.computershop.repository;

import com.example.computershop.model.entity.PrinterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PrinterRepository extends JpaRepository<PrinterEntity, Long> {

}
