package com.sparklep.preorder.domain.product.service;

import com.sparklep.preorder.domain.product.dto.ProductRequestDto;
import com.sparklep.preorder.domain.product.dto.ProductResponseDto;
import com.sparklep.preorder.domain.product.dto.ProductsResponseDto;
import com.sparklep.preorder.domain.product.entity.Product;
import com.sparklep.preorder.domain.product.entity.ProductStatusEnum;
import com.sparklep.preorder.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public void addProduct(ProductRequestDto productRequestDto) {
        Product product = Product.builder()
                .title(productRequestDto.getTitle())
                .price(productRequestDto.getPrice())
                .summary(productRequestDto.getSummary())
                .description(productRequestDto.getDescription())
                .stock(productRequestDto.getStock())
                .image(productRequestDto.getImage())
                .status(ProductStatusEnum.SELLING)
                .build();
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> getProducts(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductsResponseDto> dtoList = new ArrayList<>();

        for (Product product : products) {
            ProductsResponseDto productsResponseDto = ProductsResponseDto.of(product);
            dtoList.add(productsResponseDto);
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productNo) {
        Product product = productRepository.findByProductNo(productNo).orElseThrow(
                ()->new NullPointerException("제품 정보를 찾지 못하였습니다.")
        );
        return ProductResponseDto.description(product);
    }
}
