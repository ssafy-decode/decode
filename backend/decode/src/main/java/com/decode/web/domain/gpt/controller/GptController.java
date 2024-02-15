package com.decode.web.domain.gpt.controller;

import com.decode.web.domain.gpt.dto.GptApiAnswerResponseDto;
import com.decode.web.domain.gpt.dto.GptApiRequestDto;
import com.decode.web.domain.gpt.dto.GptApiResponseDto;
import com.decode.web.domain.gpt.service.GptApiServiceImpl;
import com.decode.web.global.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class GptController {

    private final GptApiServiceImpl gptApiService;

    @PostMapping
    public ResponseDto generateTitleAndKeywords(@RequestBody @Valid GptApiRequestDto gptApiDto) {
        GptApiResponseDto data = GptApiResponseDto.builder()
                .tagIds(gptApiService.keywordsByError(gptApiDto.getContent()))
                .titles(gptApiService.titlesByError(gptApiDto.getContent()))
                .build();

        //

        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("GPT 추천 완료")
                .data(data)
                .build();
    }


    @PostMapping("/answer")
    public ResponseDto generateAnswer(@RequestBody @Valid GptApiRequestDto gptApiDto) {
        GptApiAnswerResponseDto data = GptApiAnswerResponseDto.builder()
                .answer(gptApiService.answerByError(gptApiDto.getContent()))
                .build();

        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("GPT 답변 완료")
                .data(data)
                .build();
    }

    @PostMapping("/answer/sof")
    public ResponseDto generateSofTitle(@RequestBody @Valid GptApiRequestDto gptApiDto) {
        GptApiAnswerResponseDto data = GptApiAnswerResponseDto.builder()
                .answer(gptApiService.answerBySofError(gptApiDto.getContent()))
                .build();

        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("GPT 답변 완료")
                .data(data)
                .build();
    }

}
