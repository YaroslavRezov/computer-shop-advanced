package com.example.computershop.controller;

import com.example.computershop.service.PcService;
import com.example.specs.generated.api.PcControllerApi;
import com.example.specs.generated.model.PcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class PcController implements PcControllerApi {
    private final PcService pcService;


    @Override
    public ResponseEntity<List<PcDto>> getPcs(){
        return ResponseEntity.ok(pcService.getPcs());
    }

    @Override
    public ResponseEntity<PcDto> getPc(Long code){
        return ResponseEntity.ok(pcService.getPc(code));
    }

    @Override
    public ResponseEntity<PcDto> insertIntoPc(PcDto pcDto) {
        return ResponseEntity.ok(pcService.save(pcDto));
    }

    @Override
    public ResponseEntity<PcDto> patchPcPartially(Long code, PcDto pcDto) {
        return ResponseEntity.ok(pcService.updatePcPartially(code, pcDto));
    }

    @Override
    public ResponseEntity<Void> deleteFromPc(Long code) {
        pcService.delete(code);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/filter")
//    public Page<PcDto> filterPcs(@ParameterObject Pageable pageable) {
//        return (Page<PcDto>) pcService.getPcs();
//    }



}
