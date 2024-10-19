package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    LaptopDto insertIntoLaptop(@RequestBody LaptopDto laptopDto) {
        return laptopService.save(laptopDto);
    }
}
