package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/laptops")
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

    @PatchMapping("/{code}")
    public LaptopDto patchLaptopPartially(@PathVariable Long code, @RequestBody LaptopDto laptopDto) {
        return laptopService.updateLaptopPartially(code, laptopDto);
    }
    @DeleteMapping("/{code}")
    void deleteFromLaptop(@PathVariable("code") Long code) {
        laptopService.delete(code);

    }
}

