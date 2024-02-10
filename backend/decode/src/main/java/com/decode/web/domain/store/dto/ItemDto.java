package com.decode.web.domain.store.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ItemDto {

    private Long itemId;
    private Integer itemCount;
    private String itemName;
    private String itemDetail;
}
