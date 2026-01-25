package com.example.computershop.controller;

import com.example.computershop.service.PcService;
import com.example.specs.generated.api.PcControllerApi;
import com.example.specs.generated.model.PcDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/pcs")
public class PcController implements PcControllerApi {
    private final PcService pcService;


    @GetMapping("/all")
    public ResponseEntity<List<com.example.specs.generated.model.PcDto>> getPcs(){
        return ResponseEntity.ok(pcService.getPcs());
    }

    @GetMapping
    public ResponseEntity<com.example.specs.generated.model.PcDto> getPc(@RequestParam Long code){
        return ResponseEntity.ok(pcService.getPc(code));
    }


    @PostMapping()
    public ResponseEntity<PcDto> insertIntoPc(@Valid @RequestBody PcDto pcDto) {
        return ResponseEntity.ok(pcService.save(pcDto));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<PcDto> patchPcPartially(@PathVariable Long code, @Valid @RequestBody PcDto pcDto) {
        return ResponseEntity.ok(pcService.updatePcPartially(code, pcDto));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteFromPc(@PathVariable("code") Long code) {
        pcService.delete(code);

        return null;
    }

//    @GetMapping("/filter")
//    public Page<PcDto> filterPcs(@ParameterObject Pageable pageable) {
//        return (Page<PcDto>) pcService.getPcs();
//    }



}
