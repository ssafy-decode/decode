package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.domain.chat.dto.ChatResponseDto;
import com.decode.web.domain.chat.repository.ChatRepository;
import com.decode.web.domain.chat.repository.ChatRoomRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.ChatEntity;
import com.decode.web.entity.ChatRoomEntity;
import com.decode.web.entity.UserProfileEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    @Autowired
    @Qualifier(value = "chatRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Long saveChat(ChatRequestDto message) {
        // DB 저장
        Optional<UserProfileEntity> userProfileOptional = userProfileRepository.findById(
                message.getUserId());
        Optional<ChatRoomEntity> chatRoomOptional = chatRoomRepository.findById(
                message.getRoomId());
        ChatEntity ce = null;
        if (userProfileOptional.isPresent() && chatRoomOptional.isPresent()) {
            UserProfileEntity userProfile = userProfileOptional.get();
            ChatRoomEntity chatRoom = chatRoomOptional.get();

            ChatEntity chat = ChatEntity.builder()
                    .sender(userProfile)
                    .message(message.getText())
                    .chatRoomEntity(chatRoom)
                    .build();
            log.debug("apic ChatEntity room {}, message {}, user {}",
                    chat.getChatRoomEntity().getId(), chat.getMessage(), chat.getSender().getId());

            ce = chatRepository.save(chat);

            // 2. redis 저장
            ChatResponseDto chatResponseDto = ChatResponseDto.builder().id(ce.getId())
                    .createAt(ce.getCreatedTime().toString()).nickName(ce.getSender().getNickname())
                    .text(ce.getMessage()).userId(ce.getSender().getId()).build();
            String chatResponseDtoJson;
            try {
                chatResponseDtoJson = objectMapper.writeValueAsString(chatResponseDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            redisTemplate.opsForList()
                    .rightPush("CR:" + String.valueOf(message.getRoomId()), chatResponseDtoJson);

            // 3. expire 을 이용해서, Key 를 만료시킬 수 있음
            redisTemplate.expire("CR:" + String.valueOf(message.getRoomId()), 5, TimeUnit.MINUTES);
        } else {
            log.error("UserProfileEntity or ChatRoomEntity not found");
        }
        if (ce == null) {
            return null;
        }
        return ce.getId();
    }

    // 6. 대화 조회 - Redis & DB
    @Override
    public List<ChatResponseDto> loadMessage(Long roomId) {
        List<ChatResponseDto> messageList = new ArrayList<>();

        // RedisTemplate을 통해 리스트의 전체 범위를 조회
        List<Object> rawMessages = redisTemplate.opsForList().range("CR:"+String.valueOf(roomId), 0, 1500);
        log.debug("apic rawMessage : {}", rawMessages);

        // 4. Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (rawMessages == null || rawMessages.isEmpty()) {
            // 5.
            List<ChatEntity> chatList = chatRepository.findTop100ByChatRoomEntity_IdOrderByCreatedTimeAsc(
                    roomId);

            for (ChatEntity ce : chatList) {
                // 2. redis 저장
                ChatResponseDto chatResponseDto = ChatResponseDto.builder().id(ce.getId())
                        .createAt(ce.getCreatedTime().toString())
                        .nickName(ce.getSender().getNickname()).text(ce.getMessage())
                        .userId(ce.getSender().getId()).build();
                String chatResponseDtoJson;
                try {
                    chatResponseDtoJson = objectMapper.writeValueAsString(chatResponseDto);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                redisTemplate.opsForList()
                        .rightPush("CR:" + String.valueOf(ce.getChatRoomEntity().getId()),
                                chatResponseDtoJson);

                // 3. expire 을 이용해서, Key 를 만료시킬 수 있음
                redisTemplate.expire("CR:" + String.valueOf(ce.getId()), 5, TimeUnit.MINUTES);
                // messageList에 데이터 추가
                // ChatResponseDto 리스트에 추가
                messageList.add(chatResponseDto);

            }
        } else {
            // Redis에서 데이터가 있을 경우, 해당 데이터를 ChatResponseDto 리스트에 추가
            for (Object rawMessage : rawMessages) {
                ChatResponseDto chat = null;
                try {
                    chat = objectMapper.readValue(rawMessage.toString(), ChatResponseDto.class);
                    log.debug("ChatResponseDto : {}", chat);
                    messageList.add(chat);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return messageList;
    }

}
