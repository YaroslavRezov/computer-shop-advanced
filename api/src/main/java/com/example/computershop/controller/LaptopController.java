package com.example.computershop.controller;

import com.example.computershop.service.LaptopService;
import com.example.specs.generated.api.LaptopControllerApi;
import com.example.specs.generated.model.LaptopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LaptopController implements LaptopControllerApi{
    private final LaptopService laptopService;

    @Override
    public ResponseEntity<List<LaptopDto>> getLaptops(){
        return ResponseEntity.ok(laptopService.getLaptops());
    }
    @Override
    public ResponseEntity<LaptopDto> getLaptop(Long code){
        return ResponseEntity.ok(laptopService.getLaptop(code));
    }

    @Override
    public ResponseEntity<LaptopDto> insertIntoLaptop(LaptopDto laptopDto) {
        return ResponseEntity.ok(laptopService.save(laptopDto));
    }

    @Override
    public ResponseEntity<LaptopDto> patchLaptopPartially( Long code, LaptopDto laptopDto) {
        return ResponseEntity.ok(laptopService.updateLaptopPartially(code, laptopDto));
    }
    @Override
    public ResponseEntity<Void> deleteFromLaptop(Long code) {
        laptopService.delete(code);

        return ResponseEntity.noContent().build();
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

