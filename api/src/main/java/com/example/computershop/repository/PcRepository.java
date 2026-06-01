package com.example.computershop.repository;

import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.model.entity.PcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PcRepository extends JpaRepository<PcEntity, Long> {

}
