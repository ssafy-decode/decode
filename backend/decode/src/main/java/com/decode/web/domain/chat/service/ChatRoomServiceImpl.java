package com.decode.web.domain.chat.service;

import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.domain.chat.dto.ChatRoomResponseDto;
import com.decode.web.domain.chat.mapper.ChatRoomMapper;
import com.decode.web.domain.chat.repository.ChatRepository;
import com.decode.web.domain.chat.repository.ChatRoomRepository;
import com.decode.web.domain.chat.repository.ChatSubRoomRepository;
import com.decode.web.domain.common.redis.RedisSubscriber;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.ChatEntity;
import com.decode.web.entity.ChatRoomEntity;
import com.decode.web.entity.ChatSubRoomEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    // Topic 발행되는 메시지를 처리할 Listener
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatSubRoomRepository chatSubRoomRepository;
    private final UserProfileRepository userProfileRepository;
    @Autowired
    @Qualifier(value = "chatRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomMapper chatRoomMapper;
    // redis topic 정보. 서버별로 채팅방에 매치되는 topic info -> Map 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;
    private HashOperations<String, String, ChatRoomEntity> opsHashChatRoom;

    // redis HASH 데이터 다루기
    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new ConcurrentHashMap<>();
    }

    // GET TOPIC 과정에 NULL 이면 만드는 과정 포함
    /*
        토픽 생성
     */
    @Override
    public ChannelTopic getTopic(Long roomId) {
        log.debug("ChatRoomRepository getTopic method roomId: {}", roomId);
        // Before send message, 구독이 된 topic 확인 후 없으면 새로 생성
        return topics.computeIfAbsent(String.valueOf(roomId), id -> {
            ChannelTopic newTopic = new ChannelTopic(id);
            log.debug("new topic, roomId : {}, id : {}", roomId, id);
            redisMessageListener.addMessageListener(redisSubscriber, newTopic);
            return newTopic;
        });
    }

    /*
        채팅방 입장
     */
    @Override
    public void enterChatRoom(Long roomId) {
        ChannelTopic topic = getTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber,
                topic);        // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다
        topics.put(String.valueOf(roomId), topic);
    }

    /*
        채팅방 생성
     */
    @Override
    public Long createRoom(ChatRoomRequestDto chatRoomRequestDto) {
        // dto -> entity
        ChatRoomEntity chatRoomEntity = chatRoomMapper.toEntity(chatRoomRequestDto);
        log.debug("service createRoom ChatRoomRequestDto : {}", chatRoomRequestDto);
        return chatRoomRepository.save(chatRoomEntity).getId();
    }

    /*
        유저별 채팅방 조회
     */
    @Override
    public List<ChatRoomResponseDto> findAllRoomByUser(Long userId) {

        List<ChatSubRoomEntity> chatSubRoomList = chatSubRoomRepository.findByUserId(userId);
        return chatSubRoomList.stream()
                .map(e -> ChatRoomResponseDto.builder()
                        .id(e.getChatRoomEntity().getId())
                        .roomName(e.getChatRoomEntity().getRoomName())
                        .roomDescription(e.getChatRoomEntity().getRoomDescription())
                        .creator(e.getChatRoomEntity().getCreator())
                        .build())
                .collect(Collectors.toList());
    }

    /*
        채팅방 세부 조회
     */
    public List<ChatEntity> findAllByRoomId(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .map(chatRoomEntity -> chatRepository.findAllByChatRoomEntity_Id(roomId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "ChatRoomEntity not found with ID: " + roomId));
    }

    @Override
    public List<ChatRoomResponseDto> findAll() {
        List<ChatRoomEntity> chatRoomList = chatRoomRepository.findAll();
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();
        for (ChatRoomEntity cr : chatRoomList) {
            chatRoomResponseDtoList.add(ChatRoomResponseDto.builder().id(cr.getId())
                    .roomDescription(cr.getRoomDescription()).roomName(cr.getRoomName())
                    .creator(cr.getCreator()).build());
        }
        return chatRoomResponseDtoList;
    }

    @Override
    @Transactional
    public void subRoom(Long userId, Long roomId) throws BadRequestException {
        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findById(roomId);
        if (chatRoom.isEmpty()) {
            throw new BadRequestException("채팅방이 존재하지 않습니다.");
        }
        Optional<ChatSubRoomEntity> chatSubRoom = chatSubRoomRepository.findByChatRoomEntityIdAndUserId(
                roomId,
                userId);
        if (chatSubRoom.isEmpty()) {
            ChatSubRoomEntity chatSubRoomEntity = new ChatSubRoomEntity();
            chatSubRoomEntity.setUserId(userId);
            chatSubRoomEntity.setChatRoomEntity(chatRoom.get());
            chatSubRoomRepository.save(chatSubRoomEntity);
            return;
        }
        chatSubRoom.get().setChatRoomEntity(chatRoom.get());
    }

    @Override
    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    @Override
    public void deleteSubRoomByUserIdAndRoomId(Long userId, Long roomId)
            throws BadRequestException {
        Optional<ChatSubRoomEntity> chatSubRoom = chatSubRoomRepository.findByChatRoomEntityIdAndUserId(
                roomId, userId);
        if (chatSubRoom.isEmpty()) {
            throw new BadRequestException("방 구독 삭제 실패");
        }
        chatSubRoomRepository.delete(chatSubRoom.get());

    }
}
