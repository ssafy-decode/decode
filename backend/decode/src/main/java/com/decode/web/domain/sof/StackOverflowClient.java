package com.decode.web.domain.sof;

import com.decode.web.domain.sof.dto.SearchDto;
import com.decode.web.domain.sof.dto.SofDto;
import com.decode.web.domain.sof.dto.ItemsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StackOverflowClient {

    private static final String STACKOVERFLOW_API_URL = "https://api.stackexchange.com/search/advanced?order=desc&sort=activity&q=${q}&site=stackoverflow";

    public List<String> search(List<String> searchList) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        List<String> responses = new ArrayList<>();
        for (String q : searchList) {
            String modifiedQ = q.replace(" ", "+").replace("\"", ""); // 공백을 '+'로, 따옴표를 ''로 바꾸는 코드

            log.debug("검색한 문구 : {}", modifiedQ);
            SofDto response = restTemplate.getForObject(STACKOVERFLOW_API_URL, SofDto.class,
                    modifiedQ);
            log.debug("SOF DTO : {}", response);
            if (response != null) {
                for (ItemsDto i : response.getItems()) {
                    if (pageFilter(i)) {
                        log.debug("after page filter : {}", i);
                        responses.add(i.getLink());
                    }
                }
            }
        }
        return responses;
    }

    private boolean pageFilter(ItemsDto itemsDto) {
        return itemsDto.getAccepted_answer_id() != null
                && "Published".equals(itemsDto.getPost_state())
                && itemsDto.getIs_answered();
    }
}
