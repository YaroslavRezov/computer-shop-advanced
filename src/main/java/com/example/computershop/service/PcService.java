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
        for(PcEntity pcEntity : pcEntities){
            pcDtoList.add(new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(), pcEntity.getPrice()));
        }

        return pcDtoList;
    }

    public PcDto getPc(Long code) {
        PcEntity pcEntity = pcRepository.findById(code).orElse(null);


        return new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(),pcEntity.getPrice());
    }

    public PcDto save(PcDto requestPcDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestPcDto.getModel()).orElse(null);

        PcEntity sourcePcEntity = new PcEntity();


        sourcePcEntity.setProduct(foundProductEntity);


//        sourcePcEntity.setCode(requestPcDto.getCode());
//        sourcePcEntity.setCode(getElCode());
        sourcePcEntity.setSpeed(requestPcDto.getSpeed());
        sourcePcEntity.setRam(requestPcDto.getRam());
        sourcePcEntity.setHd(requestPcDto.getHd());
        sourcePcEntity.setCd(requestPcDto.getCd());
        sourcePcEntity.setPrice(requestPcDto.getPrice());


        PcEntity savedPcEntity = pcRepository.save(sourcePcEntity);

        PcDto responsePcDto = new PcDto();
        responsePcDto.setModel(savedPcEntity.getProduct().getModel());
        responsePcDto.setSpeed(savedPcEntity.getSpeed());
        responsePcDto.setRam(savedPcEntity.getRam());
        responsePcDto.setHd(savedPcEntity.getHd());
        responsePcDto.setCd(savedPcEntity.getCd());
        responsePcDto.setPrice(savedPcEntity.getPrice());
        responsePcDto.setCode(savedPcEntity.getCode());
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

        PcDto responsePcDto = new PcDto();
        responsePcDto.setModel(savedPcEntity.getProduct().getModel());
        responsePcDto.setSpeed(savedPcEntity.getSpeed());
        responsePcDto.setRam(savedPcEntity.getRam());
        responsePcDto.setHd(savedPcEntity.getHd());
        responsePcDto.setCd(savedPcEntity.getCd());
        responsePcDto.setPrice(savedPcEntity.getPrice());
        responsePcDto.setCode(savedPcEntity.getCode());
        return responsePcDto;
    }

    public void delete(Long code){
        pcRepository.deleteById(code);

    }
    private Long getElCode() {
        Iterable<PcEntity> pcEntities = pcRepository.findAll();
        Long elCode = Long.valueOf(0);

        for(PcEntity pcEntity : pcEntities){
            if (pcEntity.getCode() > elCode) {
                elCode = pcEntity.getCode();}
        }

        elCode++;
        return elCode;
    }
}
