package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.domain.board.service.MetooService;
import com.decode.web.global.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metoo")
public class MetooController {

    private final MetooService metooService;

    public MetooController(MetooService metooService) {
        this.metooService = metooService;
    }

    @PostMapping()
    public ResponseDto save(@RequestBody MetooDto metooDto) {
        Long metooId = metooService.save(metooDto);
        return ResponseDto.builder().status(HttpStatus.OK).data(metooId).message("나도 궁금해요 성공")
                .build();
    }

    @DeleteMapping("/{metooId}")
    public ResponseDto delete(@PathVariable String metooId) {
        return ResponseDto.builder().status(HttpStatus.OK).message("나도 궁금해요 취소").build();
    }

}
