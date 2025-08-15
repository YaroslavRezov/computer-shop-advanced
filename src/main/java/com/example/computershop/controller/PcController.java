package com.example.computershop.controller;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.service.PcService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@OpenAPIDefinition(tags = {
        @Tag(name = "create", description = "Add pc to inventory"),
        @Tag(name = "delete", description = "Delete  from inventory"),
        @Tag(name = "find", description = "Find pc from inventory"),
        @Tag(name = "update", description = "Update pc in inventory"),
        @Tag(name = "createPc", description = "Add pc to inventory"),
        @Tag(name = "deletePc", description = "Delete pc from inventory"),
        @Tag(name = "findPc", description = "Find pc from inventory"),
        @Tag(name = "updatePc", description = "Update pc in inventory")
})
@Tag(name = "tag_at_class_lvl. Its pc controller", description = "Pc controller is here")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/pcs")
public class PcController {
    private final PcService pcService;
    private final PcRepository pcRepository;
    private

    @Tag(name = "find all")
    @GetMapping("/all")
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @Tag(name = "find ")
    @GetMapping
    PcDto getPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

    @Tag(name = "create")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "createPc")
    @PostMapping()
    PcDto insertIntoPc(@Valid @RequestBody PcDto pcDto) {
        return pcService.save(pcDto);
    }

    @Tag(name = "change")
    @PatchMapping("/{code}")
    public PcDto patchPcPartially(@PathVariable Long code, @Valid @RequestBody PcDto pcDto) {
        return pcService.updatePcPartially(code, pcDto);
    }

    @Tag(name = "delete")
    @DeleteMapping("/{code}")
    void deleteFromPc(@PathVariable("code") Long code) {
        pcService.delete(code);

    }

//    @GetMapping("/filter")
//    public Page<PcDto> filterBooks(@ParameterObject Pageable pageable) {
//        return (Page<PcDto>) pcService.getPcs();
//    }



}
