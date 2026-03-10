package com.example.computershop.service;

import com.example.computershop.mapper.DeviceMapper;
import com.example.computershop.model.entity.DeviceView;
import com.example.computershop.repository.DeviceRepository;
import com.example.specs.generated.model.DeviceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static com.example.computershop.data.DeviceDtoData.createDeviceDto1;
import static com.example.computershop.data.DeviceViewData.createDeviceViewMock1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@SpringJUnitConfig(classes = {DeviceService.class})
public class DeviceServiceTest {

    @Autowired
    private DeviceService deviceService;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private DeviceMapper deviceMapper;

    private DeviceView deviceView;
    private DeviceDto deviceDto;

    @BeforeEach
    void setUp() {
        deviceView = createDeviceViewMock1();
        deviceDto = createDeviceDto1();
    }

    @Test
    void getAllDevices() {
        List<DeviceView> deviceViews = Arrays.asList(deviceView);
        List<DeviceDto> expected = Arrays.asList(deviceDto);

        when(deviceRepository.findAllDevices()).thenReturn(deviceViews);
        when(deviceMapper.toDeviceDtoList(any())).thenReturn(expected);

        List<DeviceDto> actual = deviceService.getAllDevices();

        verify(deviceRepository).findAllDevices();
        verify(deviceMapper).toDeviceDtoList(deviceViews);
        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected)
                .hasSize(1);
    }

    @Test
    void getDevice() {
        when(deviceRepository.findDeviceByCode(any())).thenReturn(deviceView);
        when(deviceMapper.toDeviceDto(any())).thenReturn(deviceDto);

        DeviceDto actual = deviceService.getDevice("1232");

        verify(deviceRepository).findDeviceByCode("1232");
        verify(deviceMapper).toDeviceDto(deviceView);
        assertThat(actual)
                .isNotNull()
                .isEqualTo(deviceDto);
    }
}
