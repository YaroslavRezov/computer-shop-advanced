package com.example.computershop.mapper;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.specs.generated.model.LaptopDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LaptopMapper {

    public LaptopEntity toLaptopEntity(LaptopDto laptopDto, ProductEntity foundProductEntity) {
        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setProduct(foundProductEntity);
        laptopEntity.setSpeed(laptopDto.getSpeed());
        laptopEntity.setRam(laptopDto.getRam());
        laptopEntity.setHd(laptopDto.getHd());
        laptopEntity.setPrice(laptopDto.getPrice());
        laptopEntity.setScreen(laptopDto.getScreen());
        return laptopEntity;
    }

    public List<LaptopDto> toLaptopDtoList(List<LaptopEntity> laptopEntities) {
        return laptopEntities.stream()
                .map(laptopEntity -> toLaptopDto(laptopEntity))
                .toList();
    }

    public LaptopDto toLaptopDto(LaptopEntity laptopEntity) {
        return new LaptopDto()
                .model(laptopEntity.getProduct().getModel())
                .speed(laptopEntity.getSpeed())
                .ram(laptopEntity.getRam())
                .hd(laptopEntity.getHd())
                .price(laptopEntity.getPrice())
                .screen(laptopEntity.getScreen())
                .code(laptopEntity.getCode());
    }
}
