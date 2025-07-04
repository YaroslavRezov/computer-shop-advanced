package com.example.computershop.service;

import com.example.computershop.model.dto.PcDto;
import com.example.computershop.model.dto.PcDto;
import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.PcEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PcService {
    private final PcRepository pcRepository;
    private final ProductRepository productRepository;

    public List<PcDto> getPcs() {
        Iterable<PcEntity> pcEntities = pcRepository.findAll();
        List<PcDto> pcDtoList = new ArrayList<>();
        for(PcEntity pcEntity : pcEntities) {
            pcDtoList.add(new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(), pcEntity.getPrice()));
        }

        return pcDtoList;
    }

    public PcDto getPc(Long code) {
        PcEntity pcEntity = pcRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ПК"));


        return new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(),pcEntity.getPrice());
    }

    public PcDto save(PcDto requestPcDto) {
        PcEntity sourcePcEntity = toPcEntity(requestPcDto);

        PcEntity savedPcEntity = pcRepository.save(sourcePcEntity);

        PcDto responsePcDto = toPcDto(savedPcEntity);

        return responsePcDto;
    }

    public PcDto updatePcPartially(@PathVariable Long code, @RequestBody PcDto pcDto) {
        PcEntity setPcEntity = pcRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ПК"));
//        ProductEntity foundProductEntity = productRepository.findById(pcDto.getModel()).orElse(null);

        if (pcDto.getSpeed() != 0) {
            setPcEntity.setSpeed(pcDto.getSpeed());
        }
        if (pcDto.getRam() != 0) {
            setPcEntity.setRam(pcDto.getRam());
        }
        if (pcDto.getHd() != 0) {
            setPcEntity.setHd(pcDto.getHd());
        }
        if (pcDto.getCd() != null) {
            setPcEntity.setCd(pcDto.getCd());
        }
        if (pcDto.getPrice() != 0) {
            setPcEntity.setPrice(pcDto.getPrice());
        }

        PcEntity savedPcEntity = pcRepository.save(setPcEntity);

        PcDto responsePcDto = toPcDto(savedPcEntity);
        return responsePcDto;
    }

    public void delete(Long code) {
        pcRepository.deleteById(code);

    }

    private PcDto toPcDto (PcEntity pcEntity) {
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

    private PcEntity toPcEntity(PcDto pcDto) {
        ProductEntity foundProductEntity = productRepository.findById(pcDto.getModel()).orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        PcEntity pcEntity = new PcEntity();

        pcEntity.setProduct(foundProductEntity);
        pcEntity.setSpeed(pcDto.getSpeed());
        pcEntity.setRam(pcDto.getRam());
        pcEntity.setHd(pcDto.getHd());
        pcEntity.setCd(pcDto.getCd());
        pcEntity.setPrice(pcDto.getPrice());
        return pcEntity;
    }
}
