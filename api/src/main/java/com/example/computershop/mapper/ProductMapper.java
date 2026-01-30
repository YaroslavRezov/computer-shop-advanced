package com.example.computershop.mapper;

import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ProductMapper {
    public ProductDto toProductDto (ProductEntity productEntity) {
        return new ProductDto()
                .maker(productEntity.getMaker())
                .type(productEntity.getType())
                .model(productEntity.getModel());
    }

    public ProductEntity toProductEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker(productDto.getMaker());
        productEntity.setType(productDto.getType());


        return productEntity;
    }

    public ProductDto toProductDtoAndGet(ProductEntity productEntity) {
        return new ProductDto()
        .maker(productEntity.getMaker())
        .model(productEntity.getModel())
        .type(productEntity.getType());
    }

    public List<ProductJoinedDto> toProductJoinedDto (Iterable<ProductJoinedView> productsJoined) {
        List<ProductJoinedDto> productJoinedDtoList = new ArrayList<>();
        for(ProductJoinedView productJoinedView : productsJoined){
            ProductJoinedDto productJoinedDto = new ProductJoinedDto();
            productJoinedDto.setMaker(productJoinedView.getMaker());
            productJoinedDto.setModel(productJoinedView.getModel());
            productJoinedDto.setType(productJoinedView.getType());
            productJoinedDto.setCode(translateDataBaseCode(String.valueOf(productJoinedView.getCode())));
            productJoinedDtoList.add(productJoinedDto);
        }
        return productJoinedDtoList;
    }

    public List<ProductDto> toProductDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(productEntity -> toProductDtoAndGet(productEntity))
                .toList();
    }

    private String translateDataBaseCode(String fromGetCode) {
        if(Objects.equals(fromGetCode, "null")){
            return "нет отделного дивайса";
        } else return fromGetCode;
    }
}
