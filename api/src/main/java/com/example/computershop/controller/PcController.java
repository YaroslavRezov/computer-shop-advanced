package com.example.computershop.controller;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.service.PcService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
//@OpenAPIDefinition(tags = {
//        @Tag(name = "create", description = "Add pc to inventory"),
//        @Tag(name = "delete", description = "Delete  from inventory"),
//        @Tag(name = "find", description = "Find pc from inventory"),
//        @Tag(name = "update", description = "Update pc in inventory"),
//        @Tag(name = "createPc", description = "Add pc to inventory"),
//        @Tag(name = "deletePc", description = "Delete pc from inventory"),
//        @Tag(name = "findPc", description = "Find pc from inventory"),
//        @Tag(name = "updatePc", description = "Update pc in inventory")
//})
@Tag(name = "tag_at_class_lvl. Its pc controller", description = "Pc controller is here")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/pcs")
public class PcController {
    private final PcService pcService;


    @GetMapping("/all")
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @Tag(name = "find ")
    @Operation(summary = "Get a pc by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the pc",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PcDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pc not found",
                    content = @Content) })
    @GetMapping
    PcDto getPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

    @Tag(name = "create")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "createPc")
    @PostMapping()
    PcDto insertIntoPc(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Pc to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PcDto.class),
                    examples = @ExampleObject(value = "{ \"code\": \"код\", \"model\": \"модель\", \"speed\": \"скорость\", \"ram\": \"рам\"}")))
    @Valid @RequestBody PcDto pcDto) {
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
//    public Page<PcDto> filterPcs(@ParameterObject Pageable pageable) {
//        return (Page<PcDto>) pcService.getPcs();
//    }



}
