package com.decode.web.domain.user.service;

public interface RedisService {
    void setValues(String key, String value);

    void setValuesWithTimeout(String key, String value, long timeout);

    String getValues(String key);

    void deleteValues(String key);
}
