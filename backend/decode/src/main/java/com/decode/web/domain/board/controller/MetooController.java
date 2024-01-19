package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.service.MetooService;
import com.decode.web.global.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metoo")
public class MetooController {

    private final MetooService metooService;

    public MetooController(MetooService metooService){
        this.metooService = metooService;
    }
    @PostMapping()
    public ResponseDto save(){

        return ResponseDto.builder().build();
    }


}
