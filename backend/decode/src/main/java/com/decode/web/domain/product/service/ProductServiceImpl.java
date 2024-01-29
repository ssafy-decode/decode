package com.decode.web.domain.product.service;

import com.decode.web.domain.product.dto.ProductDto;
import com.decode.web.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(p -> ProductDto.builder()
                        .productDetail(p.getProductDetail())
                        .productName(p.getProductName())
                        .productPrice(p.getProductPrice())
                        .productType(p.getProductType())
                        .build())
                .collect(Collectors.toList());
    }
}
