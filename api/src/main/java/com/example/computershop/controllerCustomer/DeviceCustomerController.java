package com.example.computershop.controllerCustomer;

import com.example.computershop.service.DeviceService;
import com.example.specs.generated.api.DeviceCustomerControllerApi;
import com.example.specs.generated.model.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeviceCustomerController implements DeviceCustomerControllerApi {


    private final DeviceService deviceService;

    @Override
    public ResponseEntity<List<DeviceDto>> getCustomerDevices(){
        return ResponseEntity.ok(deviceService.getAllDevices());
    }
    @Override
    public ResponseEntity<DeviceDto> getCustomerDevice(String model){
        return ResponseEntity.ok(deviceService.getDevice(model));
    }
}