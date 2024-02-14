package com.decode.web.domain.chat.controller;

import com.decode.web.domain.chat.dto.ChatResponseDto;
import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.domain.chat.dto.ChatRoomResponseDto;
import com.decode.web.domain.chat.dto.DeleteSubRoomRequestDto;
import com.decode.web.domain.chat.service.ChatRoomService;
import com.decode.web.domain.chat.service.ChatService;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/room/{roomId}/sub")
    public ResponseDto subRoom(@PathVariable Long roomId, Authentication authentication)
            throws BadRequestException {
        Long userId = (Long) authentication.getPrincipal();
        chatRoomService.subRoom(userId, roomId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("방 구독 완료")
                .data("")
                .build();
    }

    /*
        모든 방 출력
     */
    @GetMapping("/rooms")
    public ResponseDto findAll() {
        List<ChatRoomResponseDto> roomList = chatRoomService.findAll();

        return ResponseDto.builder().data(roomList).status(HttpStatus.OK).message("모든 방 리스트 불러오기")
                .build();
    }

    /*
        유저가 속해 있는 모든 방 출력
     */
    @GetMapping("/rooms/sub")
    public ResponseDto findAllByUserId(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<ChatRoomResponseDto> roomList = chatRoomService.findAllRoomByUser(userId);
        return ResponseDto.builder()
                .data(roomList)
                .status(HttpStatus.OK)
                .message("유저 방 리스트 불러오기")
                .build();
    }

    /*
        roomId로 채팅 정보 불러오기
     */
    @GetMapping("/room/{roomId}/messages")
    public ResponseDto findByRoomId(@PathVariable Long roomId) {
        List<ChatResponseDto> messageList = chatService.loadMessage(roomId);
        return ResponseDto.builder().message("해당 방 채팅 리스트 불러오기").status(HttpStatus.OK)
                .data(messageList).build();
    }

    @DeleteMapping("/room/{roomId}")
    public ResponseDto deleteByRoomId(@PathVariable Long roomId, Authentication authentication) {
        authentication.getPrincipal();
        chatRoomService.deleteRoom(roomId);
        return ResponseDto.builder()
                .message("채팅방 삭제 완료")
                .data("")
                .build();
    }

    @PostMapping("/room/sub/delete")
    public ResponseDto deleteSubRoomByUserIdAndRoomId(
            @RequestBody DeleteSubRoomRequestDto deleteSubRoomRequestDto,
            Authentication authentication) throws BadRequestException {
        authentication.getPrincipal();
        chatRoomService.deleteSubRoomByUserIdAndRoomId(deleteSubRoomRequestDto.getUserId(),
                deleteSubRoomRequestDto.getRoomId());
        return ResponseDto.builder()
                .message("방 구독 삭제 완료")
                .data("")
                .build();
    }
}
