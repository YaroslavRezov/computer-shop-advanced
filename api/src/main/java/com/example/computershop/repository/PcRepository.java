package com.example.computershop.repository;

import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.model.entity.PcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface PcRepository extends JpaRepository<PcEntity, Long> {
    @Query(nativeQuery = true, value = """
            SELECT model, price
            FROM pc
            UNION ALL
            SELECT model, price
            FROM laptop
            UNION ALL
            SELECT model, price
            FROM printer
            ORDER BY 1""")
    Iterable<DeviceView> findAllDevices();

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

}
