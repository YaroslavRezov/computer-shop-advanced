package com.example.computershop.service;

import com.example.computershop.mapper.ProductMapper;
import com.example.computershop.model.entity.*;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.specs.generated.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.specs.generated.model.ProductJoinedDto;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PcRepository pcRepository;
    private final PrinterRepository printerRepository;
    private final LaptopRepository laptopRepository;
    private final ProductMapper productMapper;
    public List<ProductDto> getProducts() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();
        return productMapper.toProductDtoList(productEntities);
    }
    public ProductDto getProduct(String model) {
        ProductEntity productEntity = productRepository.findById(model).orElseThrow(() -> new RuntimeException("Нет такого продукта"));

        return productMapper.toProductDtoAndGet(productEntity);
    }
    public List<ProductJoinedDto> getAllProductsJoined() {
        Iterable<ProductJoinedView> productsJoined = productRepository.findAllProductsJoined();

        return productMapper.toProductJoinedDto(productsJoined);
    }
    public ProductDto save(ProductDto requestProductDto) {
        ProductEntity sourceProductEntity = productMapper.toProductEntity(requestProductDto);

        ProductEntity savedProductEntity = productRepository.save(sourceProductEntity);

        return productMapper.toProductDto(savedProductEntity);
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

        return productMapper.toProductDto(savedProductEntity);
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
}
