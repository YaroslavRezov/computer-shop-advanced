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
                .model("b276a11d-c526-4f74-b3c7-95ff94bf7147")
                .price(11111111);
    }

}
