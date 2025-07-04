package com.example.computershop.service;

import com.example.computershop.model.dto.DeviceDto;
import com.example.computershop.model.dto.PrinterDto;
import com.example.computershop.model.entity.PrinterEntity;
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
            baseDeviceDtoList.add(toDeviceDtoAndGet(deviceView));
        }

        return baseDeviceDtoList;
    }

    public DeviceDto getDevice(String model) {
        DeviceView deviceView = pcRepository.findDeviceByCode(model);

        return toDeviceDtoAndGet(deviceView);
    }

    private DeviceDto toDeviceDtoAndGet(DeviceView deviceView) {
        DeviceDto deviceDto = new DeviceDto(deviceView.getModel(), deviceView.getPrice());
        return deviceDto;
    }
}
