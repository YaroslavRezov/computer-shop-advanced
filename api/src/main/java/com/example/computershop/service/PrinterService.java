package com.example.computershop.service;


import com.example.computershop.mapper.PrinterMapper;
import com.example.specs.generated.model.PrinterDto;
import com.example.computershop.model.entity.PrinterEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class PrinterService {
    private final PrinterRepository printerRepository;
    private final ProductRepository productRepository;
    private final PrinterMapper printerMapper;

    public List<PrinterDto> getPrinters() {
        Iterable<PrinterEntity> printerEntities = printerRepository.findAll();
        return printerMapper.toPrinterDtoList(printerEntities);
    }

    public PrinterDto getPrinter(Long code) {
        PrinterEntity printerEntity = printerRepository.findById(code).orElseThrow(() -> new RuntimeException("нет такаого принтера"));


        return printerMapper.toPrinterDtoAndGet(printerEntity);
    }

    public PrinterDto save(PrinterDto requestPrinterDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestPrinterDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        PrinterEntity sourcePrinterEntity = printerMapper.toPrinterEntity(requestPrinterDto, foundProductEntity);
        PrinterEntity savedPrinterEntity = printerRepository.save(sourcePrinterEntity);
        return printerMapper.toPrinterDto(savedPrinterEntity);
    }

    public PrinterDto updatePrinterPartially(Long code, PrinterDto printerDto) {
        PrinterEntity setPrinterEntity = printerRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого принтера"));
        setPrinterEntity.setColor(printerDto.getColor());
        setPrinterEntity.setType(printerDto.getType());
        setPrinterEntity.setPrice(printerDto.getPrice());
        PrinterEntity savedPrinterEntity = printerRepository.save(setPrinterEntity);

        return printerMapper.toPrinterDto(savedPrinterEntity);
    }

    public void delete(Long code){
        printerRepository.deleteById(code);
    }
}
