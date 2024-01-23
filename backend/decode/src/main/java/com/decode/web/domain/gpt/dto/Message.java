package com.decode.web.domain.gpt.dto;

import lombok.Data;

@Data
public class Message {

    private String role;
    private String content;
}

