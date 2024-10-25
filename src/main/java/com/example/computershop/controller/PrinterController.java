package com.example.computershop.controller;
import com.example.computershop.model.dto.PrinterDto;
import com.example.computershop.service.PrinterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/printers")
public class PrinterController {
    private final PrinterService printerService;

    @GetMapping("/all")
    List<PrinterDto> getPrinters(){
        return printerService.getPrinters();
    }
    @GetMapping
    PrinterDto getPrinter(@RequestParam Long code){
        return printerService.getPrinter(code);
    }

    @PostMapping()
    PrinterDto insertIntoPrinter(@RequestBody PrinterDto printerDto) {
        return printerService.save(printerDto);
    }
}
