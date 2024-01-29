package com.decode.web.domain.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private String productName;
    private Integer productPrice;
    private String productDetail;
    private Integer productType;
}
