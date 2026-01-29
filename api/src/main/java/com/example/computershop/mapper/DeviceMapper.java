package com.example.computershop.mapper;

import com.example.computershop.model.entity.DeviceView;
import com.example.specs.generated.model.DeviceDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceMapper {
    public DeviceDto toDeviceDtoAndGet(DeviceView deviceView) {
        return new DeviceDto()
                .model(deviceView.getModel())
                .price(deviceView.getPrice());
    }

    public List<DeviceDto> toDeviceDtoList(Iterable<DeviceView> BaseDevices) {
        List<DeviceDto> baseDeviceDtoList = new ArrayList<>();
        for(DeviceView deviceView : BaseDevices){
            baseDeviceDtoList.add(toDeviceDtoAndGet(deviceView));
        }
        return baseDeviceDtoList;
    }
}
