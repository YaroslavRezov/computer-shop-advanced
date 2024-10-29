package com.example.computershop.service;

import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import com.example.computershop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            productJoinedDtoList.add(new ProductJoinedDto(productJoinedView.getMaker(), productJoinedView.getModel(), productJoinedView.getType(), productJoinedView.getCode()));
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
}
