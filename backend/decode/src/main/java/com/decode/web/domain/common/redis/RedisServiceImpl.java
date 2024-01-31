package com.decode.web.domain.common.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    @Transactional
    public void setValueForSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }


    // 만료시간 설정 -> 자동 삭제
    @Override
    @Transactional
    public void setValuesWithTimeout(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getValues(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Set<String> getValuesForSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    @Transactional
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
