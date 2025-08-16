package com.example.computershop.controller;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.service.LaptopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    LaptopDto insertIntoLaptop(@Valid @RequestBody LaptopDto laptopDto) {
        return laptopService.save(laptopDto);
    }

    @PatchMapping("/{code}")
    public LaptopDto patchLaptopPartially(@PathVariable Long code, @Valid @RequestBody LaptopDto laptopDto) {
        return laptopService.updateLaptopPartially(code, laptopDto);
    }
    @DeleteMapping("/{code}")
    void deleteFromLaptop(@PathVariable("code") Long code) {
        laptopService.delete(code);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

