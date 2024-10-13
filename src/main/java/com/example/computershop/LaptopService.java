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


}
