package com.example.computershop.controller;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.service.LaptopService;
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
    LaptopDto getLaptop(@RequestParam Long code){
        return laptopService.getLaptop(code);
    }

    @PostMapping()
    LaptopDto insertIntoLaptop(@RequestBody LaptopDto laptopDto) {
        return laptopService.save(laptopDto);
    }
    @DeleteMapping("/{code}")
    void deleteFromPc(@PathVariable("code") Long code) {
        laptopService.delete(code);

    }
}

