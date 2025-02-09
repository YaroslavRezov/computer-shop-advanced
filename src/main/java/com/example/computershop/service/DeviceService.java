package com.example.computershop.service;

import com.example.computershop.model.dto.DeviceDto;
import com.example.computershop.repository.DeviceRepository;
import com.example.computershop.model.entity.DeviceView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository pcRepository;

    public List<DeviceDto> getAllDevices() {
        Iterable<DeviceView> BaseDevices = pcRepository.findAllDevices();
        List<DeviceDto> baseDeviceDtoList = new ArrayList<>();
        for(DeviceView deviceView : BaseDevices){
            baseDeviceDtoList.add(new DeviceDto(deviceView.getModel(), deviceView.getPrice()));
        }

        return baseDeviceDtoList;
    }

    public DeviceDto getDevice(String model) {
        DeviceView deviceView = pcRepository.findDeviceByCode(model);

        return new DeviceDto(deviceView.getModel(), deviceView.getPrice());
    }
}
