package com.example.computershop.repository;

import com.example.computershop.model.entity.DevicePriceView;
import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<ProductEntity, String> {
    @Query(nativeQuery = true, value = """
            SELECT model, price
            FROM pc
            UNION ALL
            SELECT model, price
            FROM laptop
            UNION ALL
            SELECT model, price
            FROM printer
            ORDER BY 1;""")
    List<DeviceView> findAllDevices();

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT ON (s.model) s.model, s.price FROM
            (SELECT  model, price
            FROM pc
            UNION
            SELECT model, price
            FROM laptop
            UNION
            SELECT model, price
            FROM printer) s
            WHERE s.model = ?1
            LIMIT 1""")
    DeviceView findDeviceByCode(String model);

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
            AND devices.model = :model
            """, nativeQuery = true)
    Optional<DevicePriceView> findDevicePriceByCode(
            @Param("code") Long code,
            @Param("model") String model);

}
