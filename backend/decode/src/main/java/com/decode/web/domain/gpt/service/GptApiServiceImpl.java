package com.decode.web.domain.gpt.service;

import com.decode.web.domain.gpt.dto.GPTResponseDto;
import com.decode.web.domain.sof.StackOverflowClient;
import com.decode.web.domain.sof.dto.SofDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptApiServiceImpl {

    private final StackOverflowClient stackOverflowClient;

    private static final String MODEL_KEY = "model";
    private static final String MESSAGES_KEY = "messages";
    private static final String ROLE_KEY = "role";
    private static final String CONTENT_KEY = "content";
    private static final String MODEL_VALUE = "gpt-4";
    private static final String SYSTEM_ROLE_VALUE = "system";
    private static final String USER_ROLE_VALUE = "user";
    private static final String KEYWORD_VALUE =
            "An error occurred while developing, so list the language used in the technology stack corresponding to the error as a keyword."
                    + "Here's an example 'Java, Spring'";
    private static final String TITLE_VALUE = "Got an error programming, but going to post it on the board to fix the error. Please recommend a suitable title in Korean.";
    private static final String ANSWER_VALUE = "You are a 10 year software engineer from FAANG."
            + "Please kindly respond to the error sent by the user in Korean.";

    private static final String SOF_TITLE_VALUE =
            "\"I need to create a search query for Stack Overflow based on error messages.\" Can you help me generate five simple questions? Each question should consist of only two keywords and be held under three words. \"Importantly, please exclude personal variables, my own file name, and special characters from keywords. I only need questions and no further information.\"";

    private static final String ANSWER_SOF_VALUE ="Please refer to the answer in the link I provided, and if you can solve the given error log, please solve it, and if not, please write that you have solved the error log based on stack overflow.\n"
            + "Please clarify that it is based on the stack overflow link provided in the answer sheet and organize it in Korean. Please do not refer to the link provided or say anything negative that does not resolve the link provided when writing the answer sheet with knowledge.";




    private final WebClient webClient;

    public List<String> keywordsByError(String error) {
        return Arrays.stream(response(error, KEYWORD_VALUE).keywords()).map(String::toLowerCase)
                .toList();
    }

    public String titlesByError(String error) {
        return response(error, TITLE_VALUE).title().trim();
    }

    public String answerByError(String error) {
        return response(error, ANSWER_VALUE).title().trim();
    }

    public String answerBySofError(String error) {
        // 링크를 convert 이용해 data gpt 전송
        List<String> list = getSofLinkByError(error);
        for (String s : list){
            log.debug("sof link : {}", s);
        }
        log.debug("error log : {}", convertListToString(error, list));
//        return response(ANSWER_SOF_VALUE,convertListToString(error, list)).title().trim();
        return response(convertListToString(error, list).trim(),
                ANSWER_SOF_VALUE).title();
    }

    public List<String> getSofLinkByError(String error) {
        // SOF 질문용 키워드 5가지 뽑아서 데이터 가공 후 SOF 전송 후 결과 값 링크 반환
        return stackOverflowClient.search(
                convertStringToList(response(error, SOF_TITLE_VALUE).title().trim()));
    }

    private GPTResponseDto response(String title, String type) {
        Map<String, Object> jsonObject = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessageMap(SYSTEM_ROLE_VALUE, type));
        messages.add(createMessageMap(USER_ROLE_VALUE, title));
        jsonObject.put(MODEL_KEY, MODEL_VALUE);
        jsonObject.put(MESSAGES_KEY, messages);
        return webClient.post().bodyValue(jsonObject).retrieve().bodyToMono(GPTResponseDto.class)
                .block();
    }

    private Map<String, String> createMessageMap(String role, String content) {
        return Map.of(ROLE_KEY, role, CONTENT_KEY, content);
    }

    private List<String> convertStringToList(String input) {
        return Arrays.stream(input.split("\n"))
                .map(s -> s.replaceAll("\\d+\\.", "").trim())
                .collect(Collectors.toList());
    }

    private String convertListToString(String prefix, List<String> input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        for (String str : input) {
            stringBuilder.append(str).append("\n");
        }
        return stringBuilder.toString();
    }
}