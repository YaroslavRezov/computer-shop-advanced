package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/laptops")
public class LaptopController {
    private final LaptopService laptopService;

    @GetMapping("/all")
    List<LaptopDto> getLaptops(){
        return laptopService.getLaptops();
    }
    @GetMapping
    LaptopDto getLaptop(@RequestParam Integer code){
        return laptopService.getLaptop(code);
    }
}
