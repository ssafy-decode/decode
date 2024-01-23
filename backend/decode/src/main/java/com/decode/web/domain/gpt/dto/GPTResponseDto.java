package com.decode.web.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class GPTResponseDto {

    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    @JsonProperty("system_fingerprint")
    private Object systemFingerprint;

    public String[] keywords() {
        return content()
                .split(", ");
    }

    public String title() {
        return content();
    }

    private String content() {
        return choices.get(0)
                .getMessage()
                .getContent()
                .trim();
    }
}
