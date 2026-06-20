package com.example.computershop.repository;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.DevicePriceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevicePriceRepository extends JpaRepository<CartDeviceProductEntity, Long> {

    @Query(value = """
            SELECT *
            FROM (
                SELECT
                    p.code,
                    p.model,
                    p.price,
                    'PC' AS type
                FROM pc p

                UNION ALL

                SELECT
                    l.code,
                    l.model,
                    l.price,
                    'LAPTOP' AS type
                FROM laptop l

                UNION ALL

                SELECT
                    pr.code,
                    pr.model,
                    pr.price,
                    'PRINTER' AS type
                FROM printer pr
            ) devices
            WHERE devices.code = :code
            """, nativeQuery = true)
    Optional<DevicePriceView> findDevicePriceByCode(
            @Param("code") Long code);
}
