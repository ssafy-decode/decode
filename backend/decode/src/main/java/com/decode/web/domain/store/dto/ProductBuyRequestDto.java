package com.decode.web.domain.store.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ProductBuyRequestDto {


    @Min(value = 0, message = "상품ID는 0 이상이어야 합니다.")
    private Long productId;

    @Min(value = 0, message = "유저ID는 0 이상이어야 합니다.")
    private Long userId;

    @Min(value = 1, message = "상품 구매는 최소 1개 이상이어야 합니다.")
    @Max(value = 10000, message = "상품 구매는 최대 10,000 이하이어야 합니다.")
    private Integer count;
}
