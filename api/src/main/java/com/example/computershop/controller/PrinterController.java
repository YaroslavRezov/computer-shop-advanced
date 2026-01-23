package com.example.computershop.controller;
import com.example.computershop.model.dto.PrinterDto;
import com.example.computershop.service.PrinterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/printers")
public class PrinterController {
    private final PrinterService printerService;

    @GetMapping("/all")
    public List<PrinterDto> getPrinters(){
        return printerService.getPrinters();
    }
    @GetMapping
    public PrinterDto getPrinter(@RequestParam Long code){
        return printerService.getPrinter(code);
    }

    @PostMapping()
    public PrinterDto insertIntoPrinter(@Valid  @RequestBody PrinterDto printerDto) {
        return printerService.save(printerDto);
    }
    @PatchMapping("/{code}")
    public PrinterDto patchPrinterPartially(@PathVariable Long code,@Valid @RequestBody PrinterDto printerDto) {
        return printerService.updatePrinterPartially(code, printerDto);
    }

    @DeleteMapping("/{code}")
    public void deleteFromPrinter(@PathVariable("code") Long code) {
        printerService.delete(code);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
