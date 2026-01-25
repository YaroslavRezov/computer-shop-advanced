package com.example.computershop.controller;
import com.example.specs.generated.model.PrinterDto;
import com.example.computershop.service.PrinterService;
import com.example.specs.generated.api.PrinterControllerApi;
import jakarta.validation.Valid;
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
@RequestMapping("/admin/printers")
public class PrinterController implements PrinterControllerApi {
    private final PrinterService printerService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<com.example.specs.generated.model.PrinterDto>> getPrinters(){
        return ResponseEntity.ok(printerService.getPrinters());
    }
    @Override
    @GetMapping
    public ResponseEntity<com.example.specs.generated.model.PrinterDto> getPrinter(@RequestParam Long code){
        return ResponseEntity.ok(printerService.getPrinter(code));
    }

    @Override
    @PostMapping()
    public ResponseEntity<PrinterDto> insertIntoPrinter(@Valid  @RequestBody PrinterDto printerDto) {
        return ResponseEntity.ok(printerService.save(printerDto));
    }
    @Override
    @PatchMapping("/{code}")
    public ResponseEntity<PrinterDto> patchPrinterPartially(@PathVariable Long code, @Valid @RequestBody PrinterDto printerDto) {
        return ResponseEntity.ok(printerService.updatePrinterPartially(code, printerDto));
    }

    @Override
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteFromPrinter(@PathVariable("code") Long code) {
        printerService.delete(code);

        return null;
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
