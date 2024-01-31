package com.decode.web.domain.common.redis;

import java.util.Set;

public interface RedisService {

    void setValue(String key, String value);

    void setValueForSet(String key, String value);

    void setValuesWithTimeout(String key, String value, long timeout);

    String getValues(String key);

    Set<String> getValuesForSet(String key);

    void deleteValues(String key);

}
