package com.decode.web.domain.store.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ItemUseDto {

    private Long userId;
    private Long itemId;
    private Integer count;
}
