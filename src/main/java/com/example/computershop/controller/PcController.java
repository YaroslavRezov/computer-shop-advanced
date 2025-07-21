package com.example.computershop.controller;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.service.PcService;
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
@RequestMapping("/admin/pcs")
public class PcController {
    private final PcService pcService;

    @GetMapping("/all")
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @GetMapping
    PcDto getPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

    @PostMapping()
    PcDto insertIntoPc(@Valid @RequestBody PcDto pcDto) {
        return pcService.save(pcDto);
    }

    @PatchMapping("/{code}")
    public PcDto patchPcPartially(@PathVariable Long code, @Valid @RequestBody PcDto pcDto) {
        return pcService.updatePcPartially(code, pcDto);
    }

    @DeleteMapping("/{code}")
    void deleteFromPc(@PathVariable("code") Long code) {
        pcService.delete(code);

    }



}
