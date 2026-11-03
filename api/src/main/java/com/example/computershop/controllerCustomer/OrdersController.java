package com.example.computershop.controllerCustomer;

import com.example.computershop.service.OrdersService;
import com.example.specs.generated.api.OrdersControllerApi;
import com.example.specs.generated.model.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class OrdersController implements OrdersControllerApi {

    private final OrdersService ordersService;

    @Override
    public ResponseEntity<List<OrdersDto>> getOrders(String username){
        return ResponseEntity.ok(ordersService.getOrders(username));
    }

//    @Override
//    public ResponseEntity<OrdersDto> patchOrdersPartially(Long code, OrdersDto ordersDto) {
//        return ResponseEntity.ok(ordersService.updateOrdersPartially(code, ordersDto));
//    }
}
