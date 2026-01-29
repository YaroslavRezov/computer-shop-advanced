package com.example.computershop.mapper;

import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.specs.generated.model.LaptopDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LaptopMapper {
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

    public LaptopDto toLaptopDtoAndGet(LaptopEntity laptopEntity) {
        return new LaptopDto()
                .code(laptopEntity.getCode())
                .model(laptopEntity.getProduct().getModel())
                .speed(laptopEntity.getSpeed())
                .ram(laptopEntity.getRam())
                .hd(laptopEntity.getHd())
                .price(laptopEntity.getPrice())
                .screen(laptopEntity.getScreen());
    }


    public List<LaptopDto> toLaptopDtoList(Iterable<LaptopEntity> laptopEntities) {
        List<LaptopDto> laptopDtoList = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopEntities) {
            laptopDtoList.add(toLaptopDtoAndGet(laptopEntity));
        }
        return laptopDtoList;
    }
}
