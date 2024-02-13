package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.domain.board.dto.MetooQuestionDto;
import com.decode.web.domain.board.service.MetooService;
import com.decode.web.global.ResponseDto;
import java.util.List;
import javax.security.auth.login.CredentialException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metoo")
@RequiredArgsConstructor
public class MetooController {

    private final MetooService metooService;

    @PostMapping()
    public ResponseDto save(@RequestBody MetooDto metooDto, Authentication auth)
            throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        if (!userId.equals(metooDto.getUserId())) {
            throw new CredentialException("사용자 불일치");
        }
        Long meTooId = metooService.save(metooDto);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(meTooId)
                .message("나도 궁금해요 성공")
                .build();
    }

    @DeleteMapping("/{questionId}")
    public ResponseDto delete(@PathVariable Long questionId, Authentication authentication)
            throws BadRequestException {
        Long userId = (Long) authentication.getPrincipal();
        metooService.delete(questionId, userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("나도 궁금해요 취소")
                .data("")
                .build();
    }

    @GetMapping("/{userId}")
    public ResponseDto get(@PathVariable Long userId)
            throws BadRequestException {
        List<MetooQuestionDto> data = metooService.get(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("나도 궁금해요 목록 가져오기")
                .data(data)
                .build();
    }

}
