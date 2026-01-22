package com.example.computershop.controllerCustomer;

import com.example.computershop.service.DeviceService;
import com.example.specs.generated.model.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/devices")
public class DeviceCustomerController {


    private final DeviceService deviceService;

    @GetMapping("/all")
    List<DeviceDto> getCustomerDevices(){
        return deviceService.getAllDevices();
    }
    @GetMapping
    DeviceDto getCustomerDevice(@RequestParam String model){
        return deviceService.getDevice(model);
    }
}