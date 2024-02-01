package com.decode.web.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.relational.core.sql.In;

@Data
@Getter
public class ItemUseDto {

    private Long userId;
    private Long itemId;
    private Integer count;
}
