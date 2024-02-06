package com.decode.web.domain.common.redis;

import java.util.Map;
import java.util.Set;

public interface RedisService {

    void incrementValueForHash(String key, String hashKey, int value);

    void setValue(String key, String value);

    void setValueForSet(String key, String value);

    void setValuesWithTimeout(String key, String value, long timeout);

    Map<String, Integer> getValuesForHash(String key);

    String getValues(String key);

    Set<String> getValuesForSet(String key);

    void deleteValues(String key);

}
