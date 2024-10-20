package com.example.computershop.controller;

import com.example.computershop.model.dto.DeviceDto;
import com.example.computershop.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/devices")
public class DeviceController {


    private final DeviceService deviceService;

    @GetMapping("/all")
    List<DeviceDto> getDevices(){
        return deviceService.getAllDevices();
    }
    @GetMapping
    DeviceDto getDevice(@RequestParam String model){
        return deviceService.getDevice(model);
    }
}