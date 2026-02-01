package com.example.computershop.controllerCustomer;

import com.example.computershop.service.CartService;
import com.example.computershop.service.PcService;
import com.example.specs.generated.api.PcCustomerControllerApi;
import com.example.specs.generated.model.CartDto;
import com.example.specs.generated.model.PcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PcCustomerController implements PcCustomerControllerApi {
    private final PcService pcService;
    private final CartService cartService;

    @Override
    public ResponseEntity<List<PcDto>> getCustomerPcs(){
        return ResponseEntity.ok(pcService.getPcs());
    }

    @Override
    public ResponseEntity<PcDto> getCustomerPc(Long code){
        return ResponseEntity.ok(pcService.getPc(code));
    }

    @Override
    public ResponseEntity<CartDto> insertCustomerPcIntoCart(CartDto cartDto) {
        return ResponseEntity.ok(cartService.save(cartDto));
    }

}
