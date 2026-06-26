package com.example.computershop.mapper;

import com.example.computershop.model.entity.CartDeviceProductEntity;
import com.example.computershop.model.entity.CartEntity;
import com.example.computershop.model.entity.DevicePriceView;
import com.example.computershop.repository.DeviceRepository;
import com.example.specs.generated.model.CartDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final DeviceRepository deviceRepository;
    public List<CartDto> toCartDtoList(List<CartDeviceProductEntity> cartDeviceProductEntities) {
        return cartDeviceProductEntities.stream()
                .map(this::toCartDto)
                .toList();
    }

    public CartDto toCartDto (CartDeviceProductEntity cartDeviceProductEntity) {
        return new CartDto()
            .cartId(cartDeviceProductEntity.getCartEntity().getCartId())
            .username(cartDeviceProductEntity.getCartEntity().getUser().getUsername())
            .model(cartDeviceProductEntity.getProductEntity().getModel())
            .type(cartDeviceProductEntity.getProductEntity().getType())
            .code(cartDeviceProductEntity.getCode())
            .price(getPriceByCodeAndModel(cartDeviceProductEntity.getCode(),
                    cartDeviceProductEntity.getProductEntity().getModel()));
    }

    private Integer getPriceByCodeAndModel(Long code, String model) {
        return deviceRepository.findDevicePriceByCode(code, model)
                .map(DevicePriceView::getPrice).orElseThrow(() ->
                        new EntityNotFoundException("нет такого дивайса: " + code));
    }
}
