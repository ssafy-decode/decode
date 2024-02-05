package com.decode.web.domain.image.controller;

import com.decode.web.domain.image.dto.ImageResponseDto;
import com.decode.web.domain.image.service.ImageService;
import com.decode.web.global.ResponseDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseDto imageUpload(
            @RequestPart(value = "file", required = false) MultipartFile image)
            throws IOException {
        String data = imageService.uploadImage(image);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("이미지 업로드 성공")
                .data(ImageResponseDto.builder()
                        .url(data)
                        .build())
                .build();
    }
}
