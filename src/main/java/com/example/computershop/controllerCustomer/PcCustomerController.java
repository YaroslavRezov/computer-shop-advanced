package com.example.computershop.controllerCustomer;

import com.example.computershop.model.dto.CartDto;
import com.example.computershop.model.dto.PcDto;
import com.example.computershop.service.CartService;
import com.example.computershop.service.PcService;
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
    List<PcDto> getPcs(){
        return pcService.getPcs();
    }
    @GetMapping
    PcDto getPc(@RequestParam Long code){
        return pcService.getPc(code);
    }

    @PostMapping()
    CartDto insertIntoCart(@RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }

}
