package com.example.computershop.mapper;

import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.specs.generated.model.PcDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PcMapper {
    public PcDto toPcDto (PcEntity pcEntity) {
        return new PcDto()
                .model(pcEntity.getProduct().getModel())
                .speed(pcEntity.getSpeed())
                .ram(pcEntity.getRam())
                .hd(pcEntity.getHd())
                .cd(pcEntity.getCd())
                .price(pcEntity.getPrice())
                .code(pcEntity.getCode());
    }


    public PcEntity toPcEntity(PcDto pcDto, ProductEntity foundProductEntity) {
        PcEntity pcEntity = new PcEntity();

        pcEntity.setProduct(foundProductEntity);
        pcEntity.setSpeed(pcDto.getSpeed());
        pcEntity.setRam(pcDto.getRam());
        pcEntity.setHd(pcDto.getHd());
        pcEntity.setCd(pcDto.getCd());
        pcEntity.setPrice(pcDto.getPrice());
        return pcEntity;
    }
    public PcDto toPcDtoAndGet(PcEntity pcEntity) {
        return new PcDto()
                .model(pcEntity.getProduct().getModel())
                .speed(pcEntity.getSpeed())
                .ram(pcEntity.getRam())
                .hd(pcEntity.getHd())
                .cd(pcEntity.getCd())
                .price(pcEntity.getPrice())
                .code(pcEntity.getCode());
    }

    public List<PcDto> toPcDtoList(Iterable<PcEntity> pcEntities) {
        List<PcDto> pcDtoList = new ArrayList<>();
        for(PcEntity pcEntity : pcEntities) {
            pcDtoList.add(toPcDtoAndGet(pcEntity));
        }
        return pcDtoList;
    }
}
