package com.example.computershop.service;

import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.model.dto.LaptopDto;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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


//        sourceLaptopEntity.setCode(requestLaptopDto.getCode());
//        sourceLaptopEntity.setCode(getElCode());
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
    public LaptopDto updateLaptopPartially(@PathVariable Long code, @RequestBody LaptopDto laptopDto) {
        LaptopEntity setLaptopEntity = laptopRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ноута"));
//        ProductEntity foundProductEntity = productRepository.findById(laptopDto.getModel()).orElse(null);

        if (laptopDto.getSpeed() != 0) {
            setLaptopEntity.setSpeed(laptopDto.getSpeed());
        }
        if (laptopDto.getRam() != 0) {
            setLaptopEntity.setRam(laptopDto.getRam());
        }
        if (laptopDto.getHd() != 0) {
            setLaptopEntity.setHd(laptopDto.getHd());
        }
        if (laptopDto.getPrice() != 0) {
            setLaptopEntity.setPrice(laptopDto.getPrice());
        }
        if (laptopDto.getScreen() != 0) {
            setLaptopEntity.setScreen(laptopDto.getScreen());
        }

        LaptopEntity savedLaptopEntity = laptopRepository.save(setLaptopEntity);

        LaptopDto responseLaptopDto = new LaptopDto();
        responseLaptopDto.setModel(savedLaptopEntity.getProduct().getModel());
        responseLaptopDto.setSpeed(savedLaptopEntity.getSpeed());
        responseLaptopDto.setRam(savedLaptopEntity.getRam());
        responseLaptopDto.setHd(savedLaptopEntity.getHd());
        responseLaptopDto.setScreen(savedLaptopEntity.getScreen());
        responseLaptopDto.setPrice(savedLaptopEntity.getPrice());
        responseLaptopDto.setCode(savedLaptopEntity.getCode());
        return responseLaptopDto;
    }

    public void delete(Long code){
        laptopRepository.deleteById(code);

    }
    private Long getElCode() {
        Iterable<LaptopEntity> laptopEntities = laptopRepository.findAll();
        Long elCode = Long.valueOf(0);

        for(LaptopEntity laptopEntity : laptopEntities){
            if (laptopEntity.getCode() > elCode) {
            elCode = laptopEntity.getCode();}
        }

        elCode++;
        return elCode;
    }




}