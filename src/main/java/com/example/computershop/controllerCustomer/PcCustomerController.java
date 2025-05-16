package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.service.PcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/pcs")
public class PcCustomerController {
    private final PcService pcService;

    @GetMapping("/all")
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @GetMapping
    PcDto getPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

}
