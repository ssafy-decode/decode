package com.decode.web.domain.gpt.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class GptApiResponseDto {

    private String titles;
    private List<String> tagIds;

    public GptApiResponseDto() {
        tagIds = new ArrayList<>();
    }

}
