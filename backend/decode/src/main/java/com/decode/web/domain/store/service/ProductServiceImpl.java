package com.decode.web.domain.store.service;

import com.decode.web.domain.store.dto.ProductBuyRequestDto;
import com.decode.web.domain.store.dto.ProductDto;
import com.decode.web.domain.store.repository.ItemRepository;
import com.decode.web.domain.store.repository.ProductRepository;
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
    public void buyProduct(ProductBuyRequestDto dto) {
        ItemEntity item = itemRepository.findByProductIdAndUserInfoId(dto.getProductId(),
                dto.getUserId());
        ProductEntity product = item.getProduct();
        product.buy(dto.getCount(), item, item.getUserInfo().getUserProfileEntity());
    }
}
