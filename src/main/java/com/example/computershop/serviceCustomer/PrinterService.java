package com.example.computershop.serviceCustomer;


import com.example.computershop.model.dto.PrinterDto;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PrinterService {
    private final PrinterRepository printerRepository;
    private final ProductRepository productRepository;

    public List<PrinterDto> getPrinters() {
        Iterable<PrinterEntity> printerEntities = printerRepository.findAll();
        List<PrinterDto> printerDtoList = new ArrayList<>();
        for(PrinterEntity printerEntity : printerEntities){
            printerDtoList.add(new PrinterDto(printerEntity.getCode(), printerEntity.getProduct().getModel(), translateDataBaseColor(printerEntity.getColor()), printerEntity.getType(), printerEntity.getPrice()));
        }

        return printerDtoList;
    }

    public PrinterDto getPrinter(Long code) {
        PrinterEntity printerEntity = printerRepository.findById(code).orElse(null);


        return new PrinterDto(printerEntity.getCode(), printerEntity.getProduct().getModel(), translateDataBaseColor(printerEntity.getColor()), printerEntity.getType(), printerEntity.getPrice());
    }

    public PrinterDto save(PrinterDto requestPrinterDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestPrinterDto.getModel()).orElse(null);

        PrinterEntity sourcePrinterEntity = new PrinterEntity();


        sourcePrinterEntity.setProduct(foundProductEntity);

//        sourcePrinterEntity.setCode(getElCode());
//        sourcePrinterEntity.setCode(requestPrinterDto.getCode());
        sourcePrinterEntity.setColor(requestPrinterDto.getColor());
        sourcePrinterEntity.setType(requestPrinterDto.getType());
        sourcePrinterEntity.setPrice(requestPrinterDto.getPrice());


        PrinterEntity savedPrinterEntity = printerRepository.save(sourcePrinterEntity);

        PrinterDto responsePrinterDto = new PrinterDto();
        responsePrinterDto.setModel(savedPrinterEntity.getProduct().getModel());
        responsePrinterDto.setColor(savedPrinterEntity.getColor());
        responsePrinterDto.setType(savedPrinterEntity.getType());
        responsePrinterDto.setPrice(savedPrinterEntity.getPrice());
        responsePrinterDto.setCode(savedPrinterEntity.getCode());
        return responsePrinterDto;
    }

    public PrinterDto updatePrinterPartially(@PathVariable Long code, @RequestBody PrinterDto printerDto) {
        PrinterEntity setPrinterEntity = printerRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого принтера"));
//        ProductEntity foundProductEntity = productRepository.findById(printerDto.getModel()).orElse(null);

        if (printerDto.getColor() != null) {
            setPrinterEntity.setColor(printerDto.getColor());
        }
        if (printerDto.getType() != null) {
            setPrinterEntity.setType(printerDto.getType());
        }
        if (printerDto.getPrice() != 0) {
            setPrinterEntity.setPrice(printerDto.getPrice());
        }

        PrinterEntity savedPrinterEntity = printerRepository.save(setPrinterEntity);

        PrinterDto responsePrinterDto = new PrinterDto();
        responsePrinterDto.setModel(savedPrinterEntity.getProduct().getModel());
        responsePrinterDto.setColor(savedPrinterEntity.getColor());
        responsePrinterDto.setType(savedPrinterEntity.getType());
        responsePrinterDto.setPrice(savedPrinterEntity.getPrice());
        responsePrinterDto.setCode(savedPrinterEntity.getCode());
        return responsePrinterDto;
    }

    public void delete(Long code){
        printerRepository.deleteById(code);

    }

    private Long getElCode() {
        Iterable<PrinterEntity> printerEntities = printerRepository.findAll();
        Long elCode = Long.valueOf(0);

        for(PrinterEntity printerEntity : printerEntities){
            if (printerEntity.getCode() > elCode) {
                elCode = printerEntity.getCode();}
        }

        elCode++;
        return elCode;
    }
    private String translateDataBaseColor(String fromGetColor) {
        if(Objects.equals(fromGetColor, "y")){
            return "Цвтеной";
        } else if (Objects.equals(fromGetColor, "n")) {
            return "Чернобелый";
        } else return "error";
    }
}
