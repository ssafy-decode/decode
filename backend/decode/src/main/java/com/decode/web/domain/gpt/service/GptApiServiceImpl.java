package com.decode.web.domain.gpt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GptApiServiceImpl {

    private static final String MODEL_KEY = "model";
    private static final String MESSAGES_KEY = "messages";
    private static final String MODEL_VALUE = "gpt-3.5-turbo";
    private static final String ROLE_KEY = "role";
    private static final String SYSTEM_ROLE_VALUE = "system";
    private static final String USER_ROLE_VALUE = "user";
    private static final String CONTENT_KEY = "content";
    private static final String SYSTEM_CONTENT_VALUE = "You are a helpful assistant.";
    private final WebClient webClient;

    public String requestGptResponse(String string) {
        Map<String, Object> jsonObject = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessageMap(SYSTEM_ROLE_VALUE, SYSTEM_CONTENT_VALUE));
        messages.add(createMessageMap(USER_ROLE_VALUE, string));
        jsonObject.put(MODEL_KEY, MODEL_VALUE);
        jsonObject.put(MESSAGES_KEY, messages);
        return webClient.post()
                .bodyValue(jsonObject)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, String> createMessageMap(String role, String content) {
        return Map.of(ROLE_KEY, role, CONTENT_KEY, content);
    }
}