package com.example.computershop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pcs")
public class PcController {
    private final PcService pcService;

    @GetMapping("/all")
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @GetMapping
    PcDto getPc(@RequestParam Integer code){
        return pcService.getPc(code);
    }
}
