package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.PcDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.PcService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/pcs")
public class PcCustomerController {
    private final PcService pcService;
    private final CartService cartService;

    @GetMapping("/all")
    List<PcDto> getCustomerPcs(){
        return pcService.getPcs();
    }
    @GetMapping
    PcDto getCustomerPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

    @PostMapping()
    CartDto insertCustomerIntoCart(@Valid  @RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }

}
