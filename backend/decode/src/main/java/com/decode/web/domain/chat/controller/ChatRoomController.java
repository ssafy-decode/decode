package com.decode.web.domain.chat.controller;

import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.domain.chat.service.ChatRoomService;
import com.decode.web.domain.chat.service.ChatService;
import com.decode.web.entity.ChatRoomEntity;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /*
        채팅방 생성
     */
    @PostMapping("/room")
    public ResponseDto createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        Long roomId = chatRoomService.createRoom(chatRoomRequestDto);
        return ResponseDto.builder().data(roomId).build();
    }

    /*
        유저가 속해 있는 모든 방 출력
     */
    @GetMapping("/rooms")
    public ResponseDto findAllByUserId() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChatRoomEntity> roomList = chatRoomService.findAllRoomByUser(userId);
        return ResponseDto.builder().data(roomList).status(HttpStatus.OK).message("유저 방 리스트 불러오기")
                .build();
    }

    /*
        roomId로 채팅 정보 불러오기
     */
    @GetMapping("/room/{roomId}/messages")
    public ResponseDto findByRoomId(@PathVariable Long roomId) {
        log.debug("roomId : {}", roomId);
        List<Object> messageList = chatService.loadMessage(roomId);
        return ResponseDto.builder().message("해당 방 채팅 리스트 불러오기").status(HttpStatus.OK)
                .data(messageList).build();
    }

    @DeleteMapping("/room/{roomId}")
    public ResponseDto deleteByRoomId(@PathVariable Long roomId) {
//        chatRoomService.deleteRoom();
        return ResponseDto.builder().build();
    }

}
