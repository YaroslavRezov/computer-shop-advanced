package com.example.computershop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PcRepository extends CrudRepository<PcEntity, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT model, price
            FROM pc
            UNION ALL
            SELECT model, price
            FROM laptop
            UNION ALL
            SELECT model, price
            FROM printer""")
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
