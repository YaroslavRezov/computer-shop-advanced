package com.example.computershop.controllerCustomer;
import com.example.computershop.service.CartService;
import com.example.computershop.service.PrinterService;
import com.example.specs.generated.api.PrinterCustomerControllerApi;
import com.example.specs.generated.model.CartDto;
import com.example.specs.generated.model.PrinterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/printers")
public class PrinterCustomerController implements PrinterCustomerControllerApi {
    private final PrinterService printerService;
    private final CartService cartService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<PrinterDto>> getCustomerPrinters(){
        return ResponseEntity.ok(printerService.getPrinters());
    }
    @Override
    @GetMapping
    public ResponseEntity<PrinterDto> getCustomerPrinter(@RequestParam Long code){
        return ResponseEntity.ok(printerService.getPrinter(code));
    }
    @Override
    @PostMapping()
    public ResponseEntity<CartDto> insertCustomerIntoCart(@Valid  @RequestBody CartDto cartDto) {
        return ResponseEntity.ok(cartService.save(cartDto));
    }
}
