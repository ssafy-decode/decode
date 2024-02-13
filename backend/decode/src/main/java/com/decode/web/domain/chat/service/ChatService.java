package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.domain.chat.dto.ChatResponseDto;
import java.util.List;

public interface ChatService {


    Long saveChat(ChatRequestDto message);

    List<ChatResponseDto> loadMessage(Long roomId);


}
