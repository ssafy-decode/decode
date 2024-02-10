package com.decode.web.domain.store.controller;

import com.decode.web.domain.store.dto.ItemDto;
import com.decode.web.domain.store.dto.ItemUseDto;
import com.decode.web.domain.store.service.ItemServiceImpl;
import com.decode.web.global.ResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/list/{userId}")
    public ResponseDto getItemListByUserId(@PathVariable Long userId) {
        List<ItemDto> data = itemService.getItemByUserId(userId);
        return ResponseDto.builder()
                .message("내가 구매한 아이템 리스트 조회 완료")
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/use")
    @Transactional
    public ResponseDto use(@RequestBody ItemUseDto itemUseDto) throws BadRequestException {
        Integer data = itemService.useItem(itemUseDto);
        return ResponseDto.builder()
                .message("아이템 사용 완료")
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }
}
