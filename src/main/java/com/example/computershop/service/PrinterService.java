package com.example.computershop.service;


import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.model.dto.PrinterDto;

import com.example.computershop.model.entity.LaptopEntity;
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

        PrinterEntity sourcePrinterEntity = toPrinterEntity(requestPrinterDto);

        PrinterEntity savedPrinterEntity = printerRepository.save(sourcePrinterEntity);

        PrinterDto responsePrinterDto = toPrinterDto(savedPrinterEntity);
        return responsePrinterDto;
    }

    public PrinterDto updatePrinterPartially(@PathVariable Long code, @RequestBody PrinterDto printerDto) {
        PrinterEntity setPrinterEntity = printerRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого принтера"));
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

        PrinterDto responsePrinterDto = toPrinterDto(savedPrinterEntity);
        return responsePrinterDto;
    }

    public void delete(Long code){
        printerRepository.deleteById(code);

    }


    private PrinterDto toPrinterDto (PrinterEntity printerEntity) {
        PrinterDto printerDto = new PrinterDto();
        printerDto.setModel(printerEntity.getProduct().getModel());
        printerDto.setColor(printerEntity.getColor());
        printerDto.setType(printerEntity.getType());
        printerDto.setPrice(printerEntity.getPrice());
        printerDto.setCode(printerEntity.getCode());
        return printerDto;
    }

    private PrinterEntity toPrinterEntity(PrinterDto printerDto) {
        ProductEntity foundProductEntity = productRepository.findById(printerDto.getModel()).orElseThrow(() -> new RuntimeException("нет такого продукта"));
        PrinterEntity printerEntity = new PrinterEntity();

        printerEntity.setProduct(foundProductEntity);

        printerEntity.setColor(printerDto.getColor());
        printerEntity.setType(printerDto.getType());
        printerEntity.setPrice(printerDto.getPrice());


        return printerEntity;
    }

    private String translateDataBaseColor(String fromGetColor) {
        if(Objects.equals(fromGetColor, "y")){
            return "Цвтеной";
        } else if (Objects.equals(fromGetColor, "n")) {
            return "Чернобелый";
        } else return "error";
    }
}
