package com.decode.web.domain.gpt.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GptApiServiceImpl {

    private final WebClient webClient;

    public Mono<Response> gpt() {
        String payload = "{\n"
                + "    \"model\": \"gpt-3.5-turbo\",\n"
                + "    \"messages\": [\n"
                + "        {\n"
                + "            \"role\": \"user\",\n"
                + "            \"content\": \"Say this is a test!\"\n"
                + "        }\n"
                + "    ],\n"
                + "    \"temperature\": 0.7\n"
                + "}";
        return webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Response.class);
    }

    public String gpt2() {
        String payload = "{\n"
                + "    \"model\": \"gpt-3.5-turbo\",\n"
                + "    \"messages\": [\n"
                + "        {\n"
                + "            \"role\": \"user\",\n"
                + "            \"content\": \"Say this is a test!\"\n"
                + "        }\n"
                + "    ],\n"
                + "    \"temperature\": 0.7\n"
                + "}";
        return webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
