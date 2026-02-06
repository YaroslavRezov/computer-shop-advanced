package com.example.computershop.data;

import com.example.specs.generated.model.DeviceDto;

public class DeviceDtoData {

    public static DeviceDto createDeviceDto1() {
        return new DeviceDto()
                .model("1232")
                .price(600);
    }

    public static DeviceDto createDeviceDto2() {
        return new DeviceDto()
                .model("1260")
                .price(350);
    }

}
