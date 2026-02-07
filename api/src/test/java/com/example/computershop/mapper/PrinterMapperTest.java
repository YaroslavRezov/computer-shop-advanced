package com.example.computershop.mapper;

import com.example.computershop.model.entity.PrinterEntity;
import com.example.specs.generated.model.PrinterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.example.computershop.data.PrinterDtoData.createPrinterDto1;
import static com.example.computershop.data.PrinterDtoData.createPrinterDto2;
import static com.example.computershop.data.PrinterEntityData.createPrinterEntity1;
import static com.example.computershop.data.PrinterEntityData.createPrinterEntity2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {PrinterMapper.class})
public class PrinterMapperTest {

    @Autowired
    private PrinterMapper printerMapper;

    @Test
    void toPrinterDto() {
        PrinterEntity source = createPrinterEntity1();
        PrinterDto expected = createPrinterDto1();

        PrinterDto actual = printerMapper.toPrinterDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toPrinterDtoList() {
        List<PrinterEntity> source = List.of(createPrinterEntity1(), createPrinterEntity2());
        List<PrinterDto> expected = List.of(createPrinterDto1(), createPrinterDto2());

        List<PrinterDto> actual = printerMapper.toPrinterDtoList(source);

        assertEquals(expected, actual);
    }
}
