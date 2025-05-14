package com.example.computershop.controller;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.service.PcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    PcDto insertIntoPc(@RequestBody PcDto pcDto) {
        return pcService.save(pcDto);
    }

    @PatchMapping("/{code}")
    public PcDto patchPcPartially(@PathVariable Long code, @RequestBody PcDto pcDto) {
        return pcService.updatePcPartially(code, pcDto);
    }

    @DeleteMapping("/{code}")
    void deleteFromPc(@PathVariable("code") Long code) {
        pcService.delete(code);

    }


}
