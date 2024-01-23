package com.decode.web.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {

    private int index;
    private Message message;
    private Object logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
}
