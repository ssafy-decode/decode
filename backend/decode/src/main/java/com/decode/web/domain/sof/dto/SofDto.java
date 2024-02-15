package com.decode.web.domain.sof.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SofDto {
    private List<ItemsDto> items;
    private boolean has_more;
    private Integer quota_max;
    private Integer quota_remaining;
}
