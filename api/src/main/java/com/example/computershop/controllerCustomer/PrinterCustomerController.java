package com.example.computershop.controllerCustomer;
import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.PrinterDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.PrinterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/printers")
public class PrinterCustomerController {
    private final PrinterService printerService;
    private final CartService cartService;

    @GetMapping("/all")
    List<PrinterDto> getCustomerPrinters(){
        return printerService.getPrinters();
    }
    @GetMapping
    PrinterDto getCustomerPrinter(@RequestParam Long code){
        return printerService.getPrinter(code);
    }

    @PostMapping()
    CartDto insertCustomerIntoCart(@Valid  @RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }
}
