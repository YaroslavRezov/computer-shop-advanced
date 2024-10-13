package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    PrinterDto getPrinter(@RequestParam Integer code){
        return printerService.getPrinter(code);
    }
}
