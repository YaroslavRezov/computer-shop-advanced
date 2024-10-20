package com.example.computershop.service;

import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.model.dto.PrinterDto;
import com.example.computershop.model.entity.PrinterEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PrinterService {
    private final PrinterRepository printerRepository;

    public List<PrinterDto> getPrinters() {
        Iterable<PrinterEntity> printerEntities = printerRepository.findAll();
        List<PrinterDto> printerDtoList = new ArrayList<>();
        for(PrinterEntity printerEntity : printerEntities){
            printerDtoList.add(new PrinterDto(printerEntity.getCode(), printerEntity.getProduct().getModel(), printerEntity.getColor(), printerEntity.getType(), printerEntity.getPrice()));
        }

        return printerDtoList;
    }

    public PrinterDto getPrinter(Long code) {
        PrinterEntity printerEntity = printerRepository.findById(code).orElse(null);


        return new PrinterDto(printerEntity.getCode(), printerEntity.getProduct().getModel(), printerEntity.getColor(), printerEntity.getType(), printerEntity.getPrice());
    }
}
