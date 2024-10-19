package com.example.computershop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LaptopService {
    private final LaptopRepository laptopRepository;

    public List<LaptopDto> getLaptops() {
        Iterable<LaptopEntity> laptopEntities = laptopRepository.findAll();
        List<LaptopDto> laptopDtoList = new ArrayList<>();
        for(LaptopEntity laptopEntity : laptopEntities){
            laptopDtoList.add(new LaptopDto(laptopEntity.getCode(), laptopEntity.getProduct().getModel(), laptopEntity.getSpeed(), laptopEntity.getRam(),laptopEntity.getHd(), laptopEntity.getPrice(), laptopEntity.getScreen()));
        }

        return laptopDtoList;
    }

    public LaptopDto getLaptop(Integer code) {
        LaptopEntity laptopEntity = laptopRepository.findById(code).orElse(null);


        return new LaptopDto(laptopEntity.getCode(), laptopEntity.getProduct().getModel(), laptopEntity.getSpeed(), laptopEntity.getRam(),laptopEntity.getHd(),laptopEntity.getPrice(), laptopEntity.getScreen());
    }

    public LaptopDto save(LaptopDto requestLaptopDto) {
        LaptopEntity sourceLaptopEntity = new LaptopEntity();
        sourceLaptopEntity.getProduct().setModel(requestLaptopDto.getModel()); // есть ошибка 500
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
