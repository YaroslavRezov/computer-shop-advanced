package com.example.computershop.mapper;

import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.specs.generated.model.ProductDto;
import com.example.specs.generated.model.ProductJoinedDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class ProductMapper {

    public ProductEntity toProductEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker(productDto.getMaker());
        productEntity.setType(productDto.getType());
        return productEntity;
    }

    public List<ProductDto> toProductDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::toProductDto)
                .toList();
    }

    public ProductDto toProductDto (ProductEntity productEntity) {
        return new ProductDto()
                .maker(productEntity.getMaker())
                .type(productEntity.getType())
                .model(productEntity.getModel());
    }

    public List<ProductJoinedDto> toProductJoinedDtoList (List<ProductJoinedView> productsJoined) {
        return productsJoined.stream()
                .map(this::toProductJoinedDto)
                .toList();
    }

    private ProductJoinedDto toProductJoinedDto(ProductJoinedView productJoinedView) {
        return new ProductJoinedDto()
                .maker(productJoinedView.getMaker())
                .model(productJoinedView.getModel())
                .type(productJoinedView.getType())
                .code(translateDataBaseCode(String.valueOf(productJoinedView.getCode())));
    }

    private String translateDataBaseCode(String fromGetCode) {
        if(Objects.equals(fromGetCode, "null")){
            return "нет отделного дивайса";
        } else return fromGetCode;
    }
}
