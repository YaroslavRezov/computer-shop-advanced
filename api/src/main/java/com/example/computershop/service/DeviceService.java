package com.example.computershop.service;

import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.repository.DeviceRepository;
import com.example.specs.generated.model.DeviceDto;
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
        List<DeviceDto> baseDeviceDtoList = toDeviceDtoList(BaseDevices);
        return baseDeviceDtoList;
    }

    public DeviceDto getDevice(String model) {
        DeviceView deviceView = pcRepository.findDeviceByCode(model);

        return toDeviceDtoAndGet(deviceView);
    }

    private DeviceDto toDeviceDtoAndGet(DeviceView deviceView) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setModel(deviceView.getModel());
        deviceDto.setPrice(deviceView.getPrice());
        return deviceDto;
    }

    private List<DeviceDto> toDeviceDtoList(Iterable<DeviceView> BaseDevices) {
        List<DeviceDto> baseDeviceDtoList = new ArrayList<>();
        for(DeviceView deviceView : BaseDevices){
            baseDeviceDtoList.add(toDeviceDtoAndGet(deviceView));
        }
        return baseDeviceDtoList;
    }
}
