package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/laptops")
public class LaptopCustomerController {

    private final LaptopService laptopService;
    private final CartService cartService;

    @GetMapping("/all")
    List<LaptopDto> getLaptops(){
        return laptopService.getLaptops();
    }
    @GetMapping
    LaptopDto getLaptop(@RequestParam Long code){
        return laptopService.getLaptop(code);
    }

    @PostMapping()
    CartDto insertIntoCart(@RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }


}

