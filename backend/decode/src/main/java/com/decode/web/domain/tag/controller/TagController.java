package com.decode.web.domain.tag.controller;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.dto.TagIdListDto;
import com.decode.web.domain.tag.service.TagService;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/addTag")
    @Operation(summary = "태그 추가", description = "태그 이름을 입력받아 태그 추가")
    public ResponseDto addTag(@RequestBody TagDto tagName) throws BadRequestException {
        if (!tagService.addTag(tagName)) {
            throw new BadRequestException("'" + tagName + "'은 중복입니다.");
        }
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message(tagName + "add")
                .data("")
                .build();
    }

    @GetMapping("/tag/{userId}")
    public ResponseDto getTags(@PathVariable Long userId) {
        TagIdListDto data = tagService.getTagListByUserId(userId);
        return ResponseDto.builder()
                .data(data)
                .message("기술 스택 목록 가져오기 성공")
                .status(HttpStatus.OK)
                .build();
    }
}
