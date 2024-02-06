package com.decode.web.domain.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long productId;
    private String productName;
    private String productDetail;
    private String productImage;
    private Integer productPrice;
    private Integer productType;
}
