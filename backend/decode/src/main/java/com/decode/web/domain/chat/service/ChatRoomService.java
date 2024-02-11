package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.entity.ChatRoomEntity;
import java.util.List;
import org.springframework.data.redis.listener.ChannelTopic;

public interface ChatRoomService {

    Long createRoom(ChatRoomRequestDto chatRoomRequestDto);

    ChannelTopic getTopic(Long roomId);

    void enterChatRoom(Long roomId);

    List<ChatRoomEntity> findAllRoomByUser(Long userId);

    List<ChatRoomResponseDto> findAll();
}
