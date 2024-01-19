package com.decode.web.domain.tag.controller;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.mapper.TagMapper;
import com.decode.web.domain.tag.service.TagService;
import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @PostMapping("/addTag")
    public ResponseDto addTag(@RequestBody TagDto tagName) {
        if (tagService.addTag(tagName)) {
            return ResponseDto.builder().status(HttpStatus.OK).message(tagName + "add").build();
        }
        return ResponseDto.builder().status(HttpStatus.OK).message(tagName + "duplicate").build();
    }

}
