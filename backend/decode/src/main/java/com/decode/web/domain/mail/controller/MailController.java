package com.decode.web.domain.mail.controller;


import com.decode.web.domain.mail.dto.MailDto;
import com.decode.web.domain.mail.service.MailService;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    @Operation(summary = "Send mail")
    public ResponseDto sendMail(@RequestBody MailDto mailDto) {
        mailService.sendMail(mailDto);
        return ResponseDto.builder()
                .message("메일 전송 성공")
                .status(HttpStatus.OK)
                .data("")
                .build();
    }
}
