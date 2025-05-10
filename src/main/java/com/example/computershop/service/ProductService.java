package com.example.computershop.service;

import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.computershop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            productDtoList.add(new ProductDto(productEntity.getMaker(), productEntity.getModel(), productEntity.getType()));
        }

        return productDtoList;
    }

    public ProductDto getProduct(String model) {
        ProductEntity productEntity = productRepository.findById(model).orElse(null);

        return new ProductDto(productEntity.getMaker(), productEntity.getModel(), productEntity.getType());
    }
    public List<ProductJoinedDto> getAllProductsJoined() {
        Iterable<ProductJoinedView> productsJoined = productRepository.findAllProductsJoined();
        List<ProductJoinedDto> productJoinedDtoList = new ArrayList<>();
        for(ProductJoinedView productJoinedView : productsJoined){
            productJoinedDtoList.add(new ProductJoinedDto(productJoinedView.getMaker(), productJoinedView.getModel(), productJoinedView.getType(), translateDataBaseCode(String.valueOf(productJoinedView.getCode()))));
        }

        return productJoinedDtoList;
    }
    public ProductDto save(ProductDto requestProductDto) {
        ProductEntity sourceProductEntity = new ProductEntity();
        sourceProductEntity.setMaker(requestProductDto.getMaker());
        sourceProductEntity.setType(requestProductDto.getType());

        ProductEntity savedProductEntity = productRepository.save(sourceProductEntity);

        ProductDto responseProductDto = new ProductDto();
        responseProductDto.setMaker(savedProductEntity.getMaker());
        responseProductDto.setType(savedProductEntity.getType());
        responseProductDto.setModel(savedProductEntity.getModel());
        return responseProductDto;
    }
    public void delete(String model){
        productRepository.deleteById(model);

    }

    public ProductDto updateProductPartially(@PathVariable String model, @RequestBody ProductDto productDto) {
        ProductEntity setProductEntity = productRepository.findById(model).orElse(null);

        if (productDto.getType() != null) {
            setProductEntity.setType(productDto.getType());
        }
        if (productDto.getMaker() != null) {
            setProductEntity.setMaker(productDto.getMaker());
        }
        if (productDto.getModel() != null) {
            setProductEntity.setModel(productDto.getModel());
        }

        productRepository.save(setProductEntity);

        ProductDto responseProductDto = new ProductDto();
        responseProductDto.setMaker(setProductEntity.getMaker());
        responseProductDto.setType(setProductEntity.getType());
        responseProductDto.setModel(setProductEntity.getModel());

        return responseProductDto;
    }

    private String translateDataBaseCode(String fromGetCode) {
        if(Objects.equals(fromGetCode, "null")){
            return "нет отделного дивайса";
        } else return fromGetCode;
    }
}
