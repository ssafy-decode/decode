package com.decode.web.domain.store.controller;

import com.decode.web.domain.store.dto.ProductBuyRequestDto;
import com.decode.web.domain.store.dto.ProductDto;
import com.decode.web.domain.store.service.ProductServiceImpl;
import com.decode.web.global.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping("/list")
    public ResponseDto findAllProducts() {
        List<ProductDto> data = productService.findAll();
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(data)
                .message("상품 전체 조회 완료")
                .build();
    }

    @GetMapping("/{productName}")
    public ResponseDto findAllByName(@PathVariable String productName) {
        List<ProductDto> data = productService.findAllByName(productName);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(data)
                .message("상품 상세 조회 완료")
                .build();
    }

    @PostMapping
    public ResponseDto buyProduct(@RequestBody @Valid ProductBuyRequestDto productBuyRequestDto) {
        productService.buyProduct(productBuyRequestDto);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(0)
                .message("상품 구매 완료")
                .build();
    }
}
