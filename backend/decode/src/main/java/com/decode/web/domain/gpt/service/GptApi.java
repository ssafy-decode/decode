package com.decode.web.domain.gpt.service;

import com.decode.web.global.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GptApi {

    private final GptApiServiceImpl gptApiService;

    @GetMapping("/gpt")
    public ResponseDto test() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(
                gptApiService.requestGptResponse("Who won the world series in 2020?"),
                JsonNode.class);
        return ResponseDto.builder()
                .message("ok")
                .status(HttpStatus.OK)
                .data(jsonNode)
                .build();
    }

}
