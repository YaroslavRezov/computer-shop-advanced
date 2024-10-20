package com.example.computershop.service;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LaptopService {
    private final LaptopRepository laptopRepository;
    private final ProductRepository productRepository;


    public List<LaptopDto> getLaptops() {
        Iterable<LaptopEntity> laptopEntities = laptopRepository.findAll();
        List<LaptopDto> laptopDtoList = new ArrayList<>();
        for(LaptopEntity laptopEntity : laptopEntities){
            laptopDtoList.add(new LaptopDto(laptopEntity.getCode(), laptopEntity.getProduct().getModel(), laptopEntity.getSpeed(), laptopEntity.getRam(),laptopEntity.getHd(), laptopEntity.getPrice(), laptopEntity.getScreen()));
        }

        return laptopDtoList;
    }

    public LaptopDto getLaptop(Long code) {
        LaptopEntity laptopEntity = laptopRepository.findById(code).orElse(null);


        return new LaptopDto(laptopEntity.getCode(), laptopEntity.getProduct().getModel(), laptopEntity.getSpeed(), laptopEntity.getRam(),laptopEntity.getHd(),laptopEntity.getPrice(), laptopEntity.getScreen());
    }

    public LaptopDto save(LaptopDto requestLaptopDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestLaptopDto.getModel()).orElse(null);

        LaptopEntity sourceLaptopEntity = new LaptopEntity();


        sourceLaptopEntity.setProduct(foundProductEntity);

        sourceLaptopEntity.setSpeed(requestLaptopDto.getSpeed());
        sourceLaptopEntity.setRam(requestLaptopDto.getRam());
        sourceLaptopEntity.setHd(requestLaptopDto.getHd());
        sourceLaptopEntity.setPrice(requestLaptopDto.getPrice());
        sourceLaptopEntity.setScreen(requestLaptopDto.getScreen());

        LaptopEntity savedLaptopEntity = laptopRepository.save(sourceLaptopEntity);

        LaptopDto responseLaptopDto = new LaptopDto();
        responseLaptopDto.setModel(savedLaptopEntity.getProduct().getModel());
        responseLaptopDto.setSpeed(savedLaptopEntity.getSpeed());
        responseLaptopDto.setRam(savedLaptopEntity.getRam());
        responseLaptopDto.setHd(savedLaptopEntity.getHd());
        responseLaptopDto.setPrice(savedLaptopEntity.getPrice());
        responseLaptopDto.setScreen(savedLaptopEntity.getScreen());
        responseLaptopDto.setCode(savedLaptopEntity.getCode());
        return responseLaptopDto;
    }


}