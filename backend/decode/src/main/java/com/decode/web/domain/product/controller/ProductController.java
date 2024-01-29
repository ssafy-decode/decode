package com.decode.web.domain.product.controller;

import com.decode.web.domain.product.dto.ProductDto;
import com.decode.web.domain.product.service.ProductServiceImpl;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseDto findAllProducts() {
        List<ProductDto> data = productService.findAll();
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(data)
                .headers(HttpHeaders.EMPTY)
                .message("상품 조회 완료")
                .build();
    }

    @PostMapping
    public ResponseDto buyProduct() {
//        productService.buyProduct(1, 2L, 124L);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(0)
                .headers(HttpHeaders.EMPTY)
                .message("상품 조회 완료")
                .build();
    }
}
