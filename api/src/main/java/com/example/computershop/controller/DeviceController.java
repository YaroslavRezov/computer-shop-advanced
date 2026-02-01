package com.example.computershop.controller;

import com.example.computershop.service.DeviceService;
import com.example.specs.generated.api.DeviceControllerApi;
import com.example.specs.generated.model.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeviceController implements DeviceControllerApi {

    private final DeviceService deviceService;

    @Override
    public ResponseEntity<DeviceDto> getDevice(String model) {
        return ResponseEntity.ok(deviceService.getDevice(model));
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }


}