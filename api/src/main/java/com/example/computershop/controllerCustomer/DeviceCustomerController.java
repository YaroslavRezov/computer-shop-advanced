package com.example.computershop.controllerCustomer;

import com.example.computershop.service.DeviceService;
import com.example.specs.generated.api.DeviceCustomerControllerApi;
import com.example.specs.generated.model.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeviceCustomerController implements DeviceCustomerControllerApi {


    private final DeviceService deviceService;

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDto>> getCustomerDevices(){
        return ResponseEntity.ok(deviceService.getAllDevices());
    }
    @GetMapping
    public ResponseEntity<DeviceDto> getCustomerDevice(@RequestParam String model){
        return ResponseEntity.ok(deviceService.getDevice(model));
    }
}