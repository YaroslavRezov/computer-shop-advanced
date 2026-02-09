package com.example.computershop.data;

import com.example.computershop.model.entity.DeviceView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceViewData {

    public static DeviceView createDeviceViewMock1() {
        DeviceView device = mock(DeviceView.class);
        when(device.getModel()).thenReturn("1232");
        when(device.getPrice()).thenReturn(600);
        return device;
    }

    public static DeviceView createDeviceViewMock1(String model) {
        DeviceView device = mock(DeviceView.class);
        when(device.getModel()).thenReturn(model);
        when(device.getPrice()).thenReturn(600);
        return device;
    }

    public static DeviceView createDeviceViewMock2() {
        DeviceView device = mock(DeviceView.class);
        when(device.getModel()).thenReturn("b276a11d-c526-4f74-b3c7-95ff94bf7147");
        when(device.getPrice()).thenReturn(11111111);
        return device;
    }

    public static DeviceView createDeviceViewMock2(String model) {
        DeviceView device = mock(DeviceView.class);
        when(device.getModel()).thenReturn(model);
        when(device.getPrice()).thenReturn(11111111);
        return device;
    }

}
