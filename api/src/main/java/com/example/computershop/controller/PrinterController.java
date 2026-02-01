package com.example.computershop.controller;
import com.example.specs.generated.model.PrinterDto;
import com.example.computershop.service.PrinterService;
import com.example.specs.generated.api.PrinterControllerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PrinterController implements PrinterControllerApi {

    private final PrinterService printerService;

    @Override
    public ResponseEntity<List<PrinterDto>> getPrinters(){
        return ResponseEntity.ok(printerService.getPrinters());
    }

    @Override
    public ResponseEntity<PrinterDto> getPrinter(Long code){
        return ResponseEntity.ok(printerService.getPrinter(code));
    }

    @Override
    public ResponseEntity<PrinterDto> insertIntoPrinter(PrinterDto printerDto) {
        return ResponseEntity.ok(printerService.save(printerDto));
    }

    @Override
    public ResponseEntity<PrinterDto> patchPrinterPartially(Long code, PrinterDto printerDto) {
        return ResponseEntity.ok(printerService.updatePrinterPartially(code, printerDto));
    }

    @Override
    public ResponseEntity<Void> deleteFromPrinter(Long code) {
        printerService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
