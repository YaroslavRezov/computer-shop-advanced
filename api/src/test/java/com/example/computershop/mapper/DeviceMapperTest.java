package com.example.computershop.mapper;

import com.example.computershop.model.entity.DeviceView;
import com.example.specs.generated.model.DeviceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

import static com.example.computershop.data.DeviceDtoData.createDeviceDto1;
import static com.example.computershop.data.DeviceDtoData.createDeviceDto2;
import static com.example.computershop.data.DeviceViewData.createDeviceViewMock1;
import static com.example.computershop.data.DeviceViewData.createDeviceViewMock2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {DeviceMapper.class})
public class DeviceMapperTest {

    @Autowired
    private DeviceMapper deviceMapper;

    @Test
    void toDeviceDto() {
        DeviceView source = createDeviceViewMock1();
        DeviceDto expected = createDeviceDto1();

        DeviceDto actual = deviceMapper.toDeviceDto(source);

        assertEquals(expected, actual);
    }

    @Test
    void toDeviceDtoList() {
        List<DeviceView> source = List.of(createDeviceViewMock1(), createDeviceViewMock2());
        List<DeviceDto> expected = List.of(createDeviceDto1(), createDeviceDto2());

        List<DeviceDto> actual = deviceMapper.toDeviceDtoList(source);

        assertEquals(expected, actual);
    }

}
