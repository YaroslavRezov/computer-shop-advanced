package com.example.computershop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface DeviceRepository extends CrudRepository<ProductEntity, String>{
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
