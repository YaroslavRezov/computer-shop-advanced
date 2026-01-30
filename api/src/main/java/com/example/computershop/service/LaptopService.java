package com.example.computershop.service;

import com.example.computershop.mapper.LaptopMapper;
import com.example.computershop.model.entity.LaptopEntity;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.LaptopDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class LaptopService {
    private final LaptopRepository laptopRepository;
    private final ProductRepository productRepository;
    private final LaptopMapper laptopMapper;


    public List<LaptopDto> getLaptops() {
        List<LaptopEntity> laptopEntities = laptopRepository.findAll();
        List<LaptopDto> laptopDtoList = laptopMapper.toLaptopDtoList(laptopEntities);

        return laptopDtoList;
    }

    public LaptopDto getLaptop(Long code) {
        LaptopEntity laptopEntity = laptopRepository.findById(code).orElse(null);


        return laptopMapper.toLaptopDtoAndGet(laptopEntity);
    }

    public LaptopDto save(LaptopDto requestLaptopDto) {
        ProductEntity foundProductEntity = productRepository.findById(requestLaptopDto.getModel())
                .orElseThrow(() -> new RuntimeException("Нет такого продукта"));
        LaptopEntity sourceLaptopEntity = laptopMapper.toLaptopEntity(requestLaptopDto, foundProductEntity);
        LaptopEntity savedLaptopEntity = laptopRepository.save(sourceLaptopEntity);
        return laptopMapper.toLaptopDto(savedLaptopEntity);
    }

    public LaptopDto updateLaptopPartially(Long code, LaptopDto laptopDto) {
        LaptopEntity setLaptopEntity = laptopRepository.findById(code).orElseThrow(() -> new RuntimeException("Нет такого ноута"));
        setLaptopEntity.setSpeed(laptopDto.getSpeed());
        setLaptopEntity.setRam(laptopDto.getRam());
        setLaptopEntity.setHd(laptopDto.getHd());
        setLaptopEntity.setPrice(laptopDto.getPrice());
        setLaptopEntity.setScreen(laptopDto.getScreen());
        LaptopEntity savedLaptopEntity = laptopRepository.save(setLaptopEntity);

        return laptopMapper.toLaptopDto(savedLaptopEntity);
    }

    public void delete(Long code) {
        laptopRepository.deleteById(code);
    }




}