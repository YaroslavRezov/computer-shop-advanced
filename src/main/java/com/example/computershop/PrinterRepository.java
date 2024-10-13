package com.example.computershop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PrinterRepository extends CrudRepository<PrinterEntity, Integer> {

}
