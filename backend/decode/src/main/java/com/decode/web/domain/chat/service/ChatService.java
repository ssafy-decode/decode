package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import java.util.List;

public interface ChatService {


    void saveChat(ChatRequestDto message);
    List<Object> loadMessage(Long roomId);
}
