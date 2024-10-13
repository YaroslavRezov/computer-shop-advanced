package com.example.computershop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PcService {
    private final PcRepository pcRepository;

    public List<PcDto> getPcs() {
        Iterable<PcEntity> pcEntities = pcRepository.findAll();
        List<PcDto> pcDtoList = new ArrayList<>();
        for(PcEntity pcEntity : pcEntities){
            pcDtoList.add(new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(), pcEntity.getPrice()));
        }

        return pcDtoList;
    }

    public PcDto getPc(Integer code) {
        PcEntity pcEntity = pcRepository.findById(code).orElse(null);


        return new PcDto(pcEntity.getCode(), pcEntity.getProduct().getModel(), pcEntity.getSpeed(), pcEntity.getRam(),pcEntity.getHd(), pcEntity.getCd(),pcEntity.getPrice());
    }
}
