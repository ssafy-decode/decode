package com.decode.web.domain.product.service;

import com.decode.web.domain.product.dto.ProductDto;
import com.decode.web.domain.product.repository.ItemRepository;
import com.decode.web.domain.product.repository.ProductRepository;
import com.decode.web.entity.ItemEntity;
import com.decode.web.entity.ProductEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(p -> ProductDto.builder()
                        .productId(p.getId())
                        .productDetail(p.getProductDetail())
                        .productName(p.getProductName())
                        .productPrice(p.getProductPrice())
                        .productType(p.getProductType())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void buyProduct(int count, Long productId, Long userInfoId) {
        ItemEntity item = itemRepository.findByProductIdAndUserInfoId(productId, userInfoId);
        ProductEntity product = item.getProduct();
        product.buy(count, item, item.getUserInfo().getUserProfileEntity());
    }
}
