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
        List<LaptopDto> laptopDtoList = toLaptopDtoList(laptopEntities);

        return laptopDtoList;
    }

    public LaptopDto getLaptop(Long code) {
        LaptopEntity laptopEntity = laptopRepository.findById(code).orElse(null);


        return toLaptopDtoAndGet(laptopEntity);
    }

    public LaptopDto save(LaptopDto requestLaptopDto) {
        LaptopEntity sourceLaptopEntity = toLaptopEntity(requestLaptopDto);

        LaptopEntity savedLaptopEntity = laptopRepository.save(sourceLaptopEntity);

        LaptopDto responseLaptopDto = toLaptopDto(savedLaptopEntity);
        return responseLaptopDto;
    }

    public LaptopDto updateLaptopPartially(Long code, LaptopDto laptopDto) {
        LaptopEntity setLaptopEntity = laptopRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ноута"));

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

        LaptopDto responseLaptopDto = toLaptopDto(savedLaptopEntity);
        return responseLaptopDto;
    }

    public void delete(Long code) {
        laptopRepository.deleteById(code);

    }

    private LaptopDto toLaptopDto(LaptopEntity laptopEntity) {
        LaptopDto laptopDto = new LaptopDto();
        laptopDto.setModel(laptopEntity.getProduct().getModel());
        laptopDto.setSpeed(laptopEntity.getSpeed());
        laptopDto.setRam(laptopEntity.getRam());
        laptopDto.setHd(laptopEntity.getHd());
        laptopDto.setPrice(laptopEntity.getPrice());
        laptopDto.setScreen(laptopEntity.getScreen());
        laptopDto.setCode(laptopEntity.getCode());
        return laptopDto;
    }

    private LaptopEntity toLaptopEntity(LaptopDto laptopDto) {
        ProductEntity foundProductEntity = productRepository.findById(laptopDto.getModel()).orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        LaptopEntity laptopEntity = new LaptopEntity();

        laptopEntity.setProduct(foundProductEntity);

        laptopEntity.setSpeed(laptopDto.getSpeed());
        laptopEntity.setRam(laptopDto.getRam());
        laptopEntity.setHd(laptopDto.getHd());
        laptopEntity.setPrice(laptopDto.getPrice());
        laptopEntity.setScreen(laptopDto.getScreen());

        return laptopEntity;
    }

    private LaptopDto toLaptopDtoAndGet(LaptopEntity laptopEntity) {
        LaptopDto laptopDto = new LaptopDto(laptopEntity.getCode(), laptopEntity.getProduct().getModel(), laptopEntity.getSpeed(), laptopEntity.getRam(), laptopEntity.getHd(), laptopEntity.getPrice(), laptopEntity.getScreen());
        return laptopDto;
    }


    private List<LaptopDto> toLaptopDtoList(Iterable<LaptopEntity> laptopEntities) {
        List<LaptopDto> laptopDtoList = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopEntities) {
            laptopDtoList.add(toLaptopDtoAndGet(laptopEntity));
        }
        return laptopDtoList;
    }


}