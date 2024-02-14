package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.domain.chat.dto.ChatRoomResponseDto;
import com.decode.web.entity.ChatRoomEntity;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.data.redis.listener.ChannelTopic;

public interface ChatRoomService {

    Long createRoom(ChatRoomRequestDto chatRoomRequestDto);

    ChannelTopic getTopic(Long roomId);

    void enterChatRoom(Long roomId);

    List<ChatRoomResponseDto> findAllRoomByUser(Long userId);

    List<ChatRoomResponseDto> findAll();

    void subRoom(Long userId, Long roomId) throws BadRequestException;

    void deleteRoom(Long roomId);

    void deleteSubRoomByUserIdAndRoomId(Long userId, Long roomId) throws BadRequestException;
}
