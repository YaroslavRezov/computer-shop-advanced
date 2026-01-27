package com.example.computershop.mapper;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.specs.generated.model.PrinterDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PrinterMapper {
    public PrinterDto toPrinterDto (PrinterEntity printerEntity) {
        PrinterDto printerDto = new PrinterDto();
        printerDto.setModel(printerEntity.getProduct().getModel());
        printerDto.setColor(printerEntity.getColor());
        printerDto.setType(printerEntity.getType());
        printerDto.setPrice(printerEntity.getPrice());
        printerDto.setCode(printerEntity.getCode());
        return printerDto;
    }


    public PrinterEntity toPrinterEntity(PrinterDto printerDto, ProductEntity foundProductEntity) {
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

    public PrinterDto toPrinterDtoAndGet(PrinterEntity printerEntity) {
        PrinterDto printerDto = new PrinterDto();
        printerDto.setCode(printerEntity.getCode());
        printerDto.setModel(printerEntity.getProduct().getModel());
        printerDto.setType(printerEntity.getType());
        printerDto.setColor(translateDataBaseColor(printerEntity.getColor()));
        printerDto.setPrice(printerEntity.getPrice());
        return printerDto;
    }

    public List<PrinterDto> toPrinterDtoList(Iterable<PrinterEntity> printerEntities) {
        List<PrinterDto> printerDtoList = new ArrayList<>();
        for(PrinterEntity printerEntity : printerEntities){
            printerDtoList.add(toPrinterDtoAndGet(printerEntity));
        }
        return printerDtoList;
    }
}
