package com.example.computershop.service;

import com.example.computershop.mapper.DeviceMapper;
import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.repository.DeviceRepository;
import com.example.specs.generated.model.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository pcRepository;
    private final DeviceMapper deviceMapper;

    public List<DeviceDto> getAllDevices() {
        Iterable<DeviceView> BaseDevices = pcRepository.findAllDevices();
        return deviceMapper.toDeviceDtoList(BaseDevices);
    }

    public DeviceDto getDevice(String model) {
        DeviceView deviceView = pcRepository.findDeviceByCode(model);

        return deviceMapper.toDeviceDtoAndGet(deviceView);
    }

}
