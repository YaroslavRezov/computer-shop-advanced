package com.example.computershop.mapper;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.specs.generated.model.PrinterDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class PrinterMapper {

    public PrinterEntity toPrinterEntity(PrinterDto printerDto, ProductEntity foundProductEntity) {
        PrinterEntity printerEntity = new PrinterEntity();
        printerEntity.setProduct(foundProductEntity);
        printerEntity.setColor(printerDto.getColor());
        printerEntity.setType(printerDto.getType());
        printerEntity.setPrice(printerDto.getPrice());
        return printerEntity;
    }

    public List<PrinterDto> toPrinterDtoList(List<PrinterEntity> printerEntities) {
        return printerEntities.stream()
                .map(this::toPrinterDto)
                .toList();
    }

    public PrinterDto toPrinterDto(PrinterEntity printerEntity) {
        return new PrinterDto()
                .model(printerEntity.getProduct().getModel())
                .color(translateDataBaseColor(printerEntity.getColor()))
                .type(printerEntity.getType())
                .price(printerEntity.getPrice())
                .code(printerEntity.getCode());
    }

    private String translateDataBaseColor(String fromGetColor) {
        if(Objects.equals(fromGetColor, "y")){
            return "Цвтеной";
        } else if (Objects.equals(fromGetColor, "n")) {
            return "Чернобелый";
        } else return "error";
    }
}
