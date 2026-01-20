package com.example.computershop.service;

import com.example.computershop.model.dto.ProductDto;
import com.example.computershop.model.dto.ProductJoinedDto;
import com.example.computershop.model.entity.*;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PcRepository pcRepository;
    private final PrinterRepository printerRepository;
    private final LaptopRepository laptopRepository;
    public List<ProductDto> getProducts() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDto> productDtoList = toProductDtoList(productEntities);
        return productDtoList;
    }
    public ProductDto getProduct(String model) {
        ProductEntity productEntity = productRepository.findById(model).orElseThrow(() -> new RuntimeException("Нет такого продукта"));

        return toProductDtoAndGet(productEntity);
    }
    public List<ProductJoinedDto> getAllProductsJoined() {
        Iterable<ProductJoinedView> productsJoined = productRepository.findAllProductsJoined();
        List<ProductJoinedDto> productJoinedDtoList = toProductJoinedDto(productsJoined);

        return productJoinedDtoList;
    }
    public ProductDto save(ProductDto requestProductDto) {
        ProductEntity sourceProductEntity = toProductEntity(requestProductDto);

        ProductEntity savedProductEntity = productRepository.save(sourceProductEntity);

        ProductDto responseProductDto = toProductDto(savedProductEntity);
        return responseProductDto;
    }
    public void delete(String model){
        productRepository.deleteById(model);

    }

    public ProductDto updateProductPartially(String model, ProductDto productDto) {
        ProductEntity setProductEntity = productRepository.findById(model).orElseThrow(() -> new RuntimeException("Нет такого продукта"));


        if (productDto.getMaker() != null) {
            setProductEntity.setMaker(productDto.getMaker());
        }


        ProductEntity savedProductEntity = productRepository.save(setProductEntity);

        ProductDto responseProductDto = toProductDto(savedProductEntity);

        return responseProductDto;
    }

    private String translateDataBaseCode(String fromGetCode) {
        if(Objects.equals(fromGetCode, "null")){
            return "нет отделного дивайса";
        } else return fromGetCode;
    }

    public Optional<Integer> getPriceByCode(Long code) {
        Optional<PcEntity> pc = pcRepository.findById(code);
        if (pc.isPresent()) return Optional.of(pc.get().getPrice());

        Optional<LaptopEntity> laptop = laptopRepository.findById(code);
        if (laptop.isPresent()) return Optional.of(laptop.get().getPrice());

        Optional<PrinterEntity> printer = printerRepository.findById(code);
        if (printer.isPresent()) return Optional.of(printer.get().getPrice());

        return Optional.empty(); // если не нашли
    }

    private ProductDto toProductDto (ProductEntity productEntity) {
        ProductDto responseProductDto = new ProductDto();
        responseProductDto.setMaker(productEntity.getMaker());
        responseProductDto.setType(productEntity.getType());
        responseProductDto.setModel(productEntity.getModel());
        return responseProductDto;
    }

    private ProductEntity toProductEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMaker(productDto.getMaker());
        productEntity.setType(productDto.getType());


        return productEntity;
    }

    private ProductDto toProductDtoAndGet(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto(productEntity.getMaker(), productEntity.getModel(), productEntity.getType());
        return productDto;
    }

    private List<ProductJoinedDto> toProductJoinedDto (Iterable<ProductJoinedView> productsJoined) {
        List<ProductJoinedDto> productJoinedDtoList = new ArrayList<>();
        for(ProductJoinedView productJoinedView : productsJoined){
            productJoinedDtoList.add(new ProductJoinedDto(productJoinedView.getMaker(), productJoinedView.getModel(), productJoinedView.getType(), translateDataBaseCode(String.valueOf(productJoinedView.getCode()))));
        }
        return productJoinedDtoList;
    }

    private List<ProductDto> toProductDtoList(Iterable<ProductEntity> productEntities) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            productDtoList.add(toProductDtoAndGet(productEntity));
        }
        return productDtoList;
    }
}
