package com.example.computershop.repository;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.DevicePriceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevicePriceRepository
        extends JpaRepository<CartDeviceProductEntity, Long> {

    @Query(value = """
            SELECT code, model, price, device_type
            FROM device_price_view
            WHERE code = :code
            """, nativeQuery = true)
    Optional<DevicePriceView> findPriceByCode(
            @Param("code") Long code);

}
