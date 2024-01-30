package com.decode.web.domain.store.controller;

import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService
    
    // 유저가 보유한 아이템 목록조회
    public ResponseDto getItemList() {

        return ResponseDto.builder()
                .build();
    }
}
