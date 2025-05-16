package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/laptops")
public class LaptopCustomerController {

    private final LaptopService laptopService;

    @GetMapping("/all")
    List<LaptopDto> getLaptops(){
        return laptopService.getLaptops();
    }
    @GetMapping
    LaptopDto getLaptop(@RequestParam Long code){
        return laptopService.getLaptop(code);
    }

}

