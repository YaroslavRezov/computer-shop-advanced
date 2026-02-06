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

    public static DeviceView createDeviceViewMock2() {
        DeviceView device = mock(DeviceView.class);
        when(device.getModel()).thenReturn("1260");
        when(device.getPrice()).thenReturn(350);
        return device;
    }

}
