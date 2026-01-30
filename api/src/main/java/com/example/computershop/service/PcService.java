package com.example.computershop.service;

import com.example.computershop.mapper.PcMapper;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.PcDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PcService {
    private final PcRepository pcRepository;
    private final ProductRepository productRepository;
    private final PcMapper pcMapper;

    public List<PcDto> getPcs() {
        List<PcEntity> pcEntities = pcRepository.findAll();
        return pcMapper.toPcDtoList(pcEntities);
    }

    public PcDto getPc(Long code) {
        PcEntity pcEntity = pcRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ПК"));


        return pcMapper.toPcDtoAndGet(pcEntity);
    }

    public PcDto save(PcDto requestPcDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestPcDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        PcEntity sourcePcEntity = pcMapper.toPcEntity(requestPcDto, foundProductEntity);
        PcEntity savedPcEntity = pcRepository.save(sourcePcEntity);
        return pcMapper.toPcDto(savedPcEntity);
    }

    public PcDto updatePcPartially(Long code, PcDto pcDto) {
        PcEntity setPcEntity = pcRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ПК"));
//        ProductEntity foundProductEntity = productRepository.findById(pcDto.getModel()).orElse(null);
        setPcEntity.setSpeed(pcDto.getSpeed());
        setPcEntity.setRam(pcDto.getRam());
        setPcEntity.setHd(pcDto.getHd());
        setPcEntity.setCd(pcDto.getCd());
        setPcEntity.setPrice(pcDto.getPrice());
        PcEntity savedPcEntity = pcRepository.save(setPcEntity);

        return pcMapper.toPcDto(savedPcEntity);
    }

    public void delete(Long code) {
        pcRepository.deleteById(code);
    }

}
