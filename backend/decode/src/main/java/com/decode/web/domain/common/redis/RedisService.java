package com.decode.web.domain.common.redis;

public interface RedisService {

    void setValues(String key, String value);

    void setValuesWithTimeout(String key, String value, long timeout);

    String getValues(String key);

    void deleteValues(String key);
}
