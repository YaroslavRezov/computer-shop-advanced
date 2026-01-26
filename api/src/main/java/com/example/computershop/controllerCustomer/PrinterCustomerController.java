package com.example.computershop.controllerCustomer;
import com.example.computershop.service.CartService;
import com.example.computershop.service.PrinterService;
import com.example.specs.generated.api.PrinterCustomerControllerApi;
import com.example.specs.generated.model.CartDto;
import com.example.specs.generated.model.PrinterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PrinterCustomerController implements PrinterCustomerControllerApi {
    private final PrinterService printerService;
    private final CartService cartService;

    @Override
    public ResponseEntity<List<PrinterDto>> getCustomerPrinters(){
        return ResponseEntity.ok(printerService.getPrinters());
    }
    @Override
    public ResponseEntity<PrinterDto> getCustomerPrinter(Long code){
        return ResponseEntity.ok(printerService.getPrinter(code));
    }
    @Override
    public ResponseEntity<CartDto> insertCustomerPrinterIntoCart(CartDto cartDto) {
        return ResponseEntity.ok(cartService.save(cartDto));
    }
}
