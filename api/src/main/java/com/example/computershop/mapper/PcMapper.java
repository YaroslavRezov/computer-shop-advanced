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
        PcDto pcDto = new PcDto();
        pcDto.setModel(pcEntity.getProduct().getModel());
        pcDto.setSpeed(pcEntity.getSpeed());
        pcDto.setRam(pcEntity.getRam());
        pcDto.setHd(pcEntity.getHd());
        pcDto.setCd(pcEntity.getCd());
        pcDto.setPrice(pcEntity.getPrice());
        pcDto.setCode(pcEntity.getCode());
        return pcDto;
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
        PcDto pcDto =new PcDto();
        pcDto.setCode(pcEntity.getCode());
        pcDto.setModel(pcEntity.getProduct().getModel());
        pcDto.setSpeed(pcEntity.getSpeed());
        pcDto.setRam(pcEntity.getRam());
        pcDto.setHd(pcEntity.getHd());
        pcDto.setCd(pcEntity.getCd());
        pcDto.setPrice(pcEntity.getPrice());
        return pcDto;
    }

    public List<PcDto> toPcDtoList(Iterable<PcEntity> pcEntities) {
        List<PcDto> pcDtoList = new ArrayList<>();
        for(PcEntity pcEntity : pcEntities) {
            pcDtoList.add(toPcDtoAndGet(pcEntity));
        }
        return pcDtoList;
    }
}
