package com.decode.web.domain.store.service;

import com.decode.web.domain.store.dto.ProductBuyRequestDto;
import com.decode.web.domain.store.dto.ProductDto;
import com.decode.web.domain.store.repository.ItemRepository;
import com.decode.web.domain.store.repository.ProductRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.ItemEntity;
import com.decode.web.entity.ProductEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.exception.NotEnoughCoinException;
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
    private final UserProfileRepository userProfileRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findAllByName(String productName) {
        return productRepository.findByName(productName)
                .stream()
                .map(ProductEntity::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void buyProduct(ProductBuyRequestDto dto) {
        ProductEntity product = productRepository.findById(dto.getProductId());
        UserProfileEntity userProfile = userProfileRepository.findById(dto.getUserId()).get();
        if (product.getProductPrice() > userProfile.getCoin()) {
            throw new NotEnoughCoinException("사용자 코인이 부족합니다.");
        }
        ItemEntity item = itemRepository.findByProductIdAndUserId(
                dto.getProductId(), dto.getUserId());
        item.setProduct(product);
        item.setUserInfo(userProfile.getUserInfoEntity());
        item.setProductCount(item.getProductCount() + dto.getCount());
        itemRepository.save(item);
        userProfile.decreaseCoin(product.getProductPrice());
    }
}
