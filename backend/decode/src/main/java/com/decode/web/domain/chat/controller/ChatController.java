package com.decode.web.domain.chat.controller;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.domain.chat.service.ChatRoomService;
import com.decode.web.domain.chat.service.ChatService;
import com.decode.web.domain.common.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatRequestDto message) {
        log.debug("apic test message : {}", message);
        Long roomId = message.getRoomId();
        // 방에 입장
        chatRoomService.enterChatRoom(roomId);

        // 들어온 메세지를 해당 토픽으로 뿌려준다.
        redisPublisher.publish(chatRoomService.getTopic(roomId), message);

        // DB & Redis 저장
        chatService.saveChat(message);
    }

}
