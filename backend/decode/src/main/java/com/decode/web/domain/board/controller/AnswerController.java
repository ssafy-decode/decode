package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.service.AnswerService;
import com.decode.web.domain.board.service.AnswerServiceImpl;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.global.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerServiceImpl answerServiceImpl) {
        this.answerService = answerServiceImpl;
    }

    @PostMapping()
    public ResponseDto save(CreateAnswerDto createAnswerDto) {
        Long answerId = answerService.save(createAnswerDto);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 등록 성공").data(answerId)
                .build();
    }

    @PatchMapping()
    public ResponseDto update(UpdateAnswerDto updateAnswerDto) {
        AnswerEntity answer = answerService.update(updateAnswerDto);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 업데이트 성공").data(answer)
                .build();
    }

    @DeleteMapping()
    public ResponseDto delete(Long answerId) {
        answerService.delete(answerId);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 삭제 성공").build();
    }


}
