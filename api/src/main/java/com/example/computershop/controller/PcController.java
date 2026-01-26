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
public class PcController implements PcControllerApi {
    private final PcService pcService;


    public ResponseEntity<List<PcDto>> getPcs(){
        return ResponseEntity.ok(pcService.getPcs());
    }

    public ResponseEntity<PcDto> getPc(@RequestParam Long code){
        return ResponseEntity.ok(pcService.getPc(code));
    }


    public ResponseEntity<PcDto> insertIntoPc(@Valid @RequestBody PcDto pcDto) {
        return ResponseEntity.ok(pcService.save(pcDto));
    }

    public ResponseEntity<PcDto> patchPcPartially(@PathVariable Long code, @Valid @RequestBody PcDto pcDto) {
        return ResponseEntity.ok(pcService.updatePcPartially(code, pcDto));
    }

    public ResponseEntity<Void> deleteFromPc(@PathVariable("code") Long code) {
        pcService.delete(code);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/filter")
//    public Page<PcDto> filterPcs(@ParameterObject Pageable pageable) {
//        return (Page<PcDto>) pcService.getPcs();
//    }



}
