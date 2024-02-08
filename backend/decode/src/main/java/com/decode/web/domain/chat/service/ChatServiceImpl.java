package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.domain.chat.repository.ChatRepository;
import com.decode.web.domain.chat.repository.ChatRoomRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.ChatEntity;
import com.decode.web.entity.ChatRoomEntity;
import com.decode.web.entity.UserProfileEntity;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
    public void saveChat(ChatRequestDto message) {
        // DB 저장
        Optional<UserProfileEntity> userProfileOptional = userProfileRepository.findById(
                message.getUserId());
        Optional<ChatRoomEntity> chatRoomOptional = chatRoomRepository.findById(
                message.getRoomId());

        if (userProfileOptional.isPresent() && chatRoomOptional.isPresent()) {
            UserProfileEntity userProfile = userProfileOptional.get();
            ChatRoomEntity chatRoom = chatRoomOptional.get();

            ChatEntity chat = ChatEntity.builder()
                    .sender(userProfile)
                    .message(message.getMessage())
                    .chatRoomEntity(chatRoom)
                    .build();
//            log.debug("apic UserProfileEntity : {}", userProfile);
//            log.debug("apic ChatRoomEntity : {}", chatRoom);
            log.debug("apic ChatEntity room {}, message {}, user {}",
                    chat.getChatRoomEntity().getId(), chat.getMessage(), chat.getSender().getId());

            chatRepository.save(chat);

            // 2. redis 저장
            redisTemplate.opsForList().rightPush(String.valueOf(message.getRoomId()), chat);

            // 3. expire 을 이용해서, Key 를 만료시킬 수 있음
            redisTemplate.expire(String.valueOf(message.getRoomId()), 5, TimeUnit.MINUTES);
        } else {
            log.error("UserProfileEntity or ChatRoomEntity not found");
        }

    }

    // 6. 대화 조회 - Redis & DB
    @Override
    public List<Object> loadMessage(Long roomId) {
        List<Object> messageList = new ArrayList<>();

        // RedisTemplate을 통해 리스트의 전체 범위를 조회
        List<Object> rawMessages = redisTemplate.opsForList().range(String.valueOf(roomId), 0, 99);
        log.debug("apic rawMessage : {}", rawMessages);

        // 4. Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (rawMessages == null || rawMessages.isEmpty()) {
            // 5.
            List<ChatEntity> chatList = chatRepository.findTop100ByChatRoomEntity_IdOrderByCreatedTimeAsc(
                    roomId);

            for (ChatEntity chat : chatList) {
                redisTemplate.setValueSerializer(
                        new Jackson2JsonRedisSerializer<>(ChatEntity.class));      // 직렬화
                redisTemplate.opsForList().rightPush(String.valueOf(roomId),
                        chat);                                // redis 저장
            }
        } else {
            // 7.
            messageList.addAll(rawMessages);
        }

        return messageList;
    }

}
