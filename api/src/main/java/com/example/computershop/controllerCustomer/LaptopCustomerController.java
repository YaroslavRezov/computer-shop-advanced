package com.example.computershop.controllerCustomer;

import com.example.computershop.service.CartService;
import com.example.computershop.service.LaptopService;
import com.example.specs.generated.api.LaptopCustomerControllerApi;
import com.example.specs.generated.model.CartDto;
import com.example.specs.generated.model.LaptopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LaptopCustomerController implements LaptopCustomerControllerApi {

    private final LaptopService laptopService;
    private final CartService cartService;

    @Override
    public ResponseEntity<List<LaptopDto>> getCustomerLaptops(){
        return ResponseEntity.ok(laptopService.getLaptops());
    }

    @Override
    public ResponseEntity<LaptopDto> getCustomerLaptop(Long code){
        return ResponseEntity.ok(laptopService.getLaptop(code));
    }

    @Override
    public ResponseEntity<CartDto> insertCustomerLaptopIntoCart(CartDto cartDto) {
        return ResponseEntity.ok(cartService.save(cartDto));
    }
}

