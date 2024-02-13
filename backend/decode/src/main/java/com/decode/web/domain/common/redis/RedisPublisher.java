package com.decode.web.domain.common.redis;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.domain.chat.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    @Autowired
    @Qualifier(value = "chatRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatResponseDto message) {
        log.debug("topic : {}, message : {}", topic.getTopic(), message);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}