package com.sparklep.preorder.domain.product.controller;

import com.sparklep.preorder.domain.product.dto.ProductRequestDto;
import com.sparklep.preorder.domain.product.dto.ProductResponseDto;
import com.sparklep.preorder.domain.product.dto.ProductsResponseDto;
import com.sparklep.preorder.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.addProduct(productRequestDto);
        return "add";
    }
    @GetMapping
    public List<ProductsResponseDto> getProducts(@RequestParam(value="page", defaultValue = "0") int page,
                                     @RequestParam(value="size") int size,
                                     @RequestParam(value="sortBy", required = false, defaultValue = "modifiedAt") String sortBy){
        return productService.getProducts(page,size,sortBy);
    }
    @GetMapping("/{productNo}")
    public ProductResponseDto getProduct(@PathVariable(value = "productNo") Long productNo) {
        return productService.getProduct(productNo);
    }
}
