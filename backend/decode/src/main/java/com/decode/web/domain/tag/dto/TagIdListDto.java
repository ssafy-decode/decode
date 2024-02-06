package com.decode.web.domain.tag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagIdListDto {

    List<Long> tagIdList;

}
