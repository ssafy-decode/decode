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

    private final WebClient webClient;

    private static final String MODEL_KEY = "model";
    private static final String MESSAGES_KEY = "messages";
    private static final String ROLE_KEY = "role";
    private static final String CONTENT_KEY = "content";
    private static final String MODEL_VALUE = "gpt-3.5-turbo";
    private static final String SYSTEM_ROLE_VALUE = "system";
    private static final String USER_ROLE_VALUE = "user";
    private static final String KEYWORD_VALUE =
            "An error occurred while developing, so list the language used in the technology stack corresponding to the error as a keyword. Here's an example 'Java, Spring'";
    private static final String TITLE_VALUE =
            "Got an error programming, but going to post it on the board to fix the error. Please recommend a suitable title."
                    + "Don't do a simple translation."
                    + "The title should be no more than 30 characters."
                    + "Only title.";

    public String keywordByError(String error) {
        return response(error, KEYWORD_VALUE);
    }

    public String titleByError(String title) {
        return response(title, TITLE_VALUE);
    }

    private String response(String title, String type) {
        Map<String, Object> jsonObject = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessageMap(SYSTEM_ROLE_VALUE, type));
        messages.add(createMessageMap(USER_ROLE_VALUE, title));
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