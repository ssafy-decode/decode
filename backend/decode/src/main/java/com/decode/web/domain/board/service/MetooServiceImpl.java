package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.entity.MetooEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetooServiceImpl implements MetooService {

    private final MetooRepository metooRepository;
    private final UserInfoRepository userInfoRepository;
    private final QuestionRepository questionRepository;


    @Override
    public Long save(MetooDto metooDto) {
        // dto -> entity
        UserInfoEntity userInfo = userInfoRepository.getReferenceById(metooDto.getUserId());
        QuestionEntity question = questionRepository.getReferenceById(metooDto.getQuestionId());

        return metooRepository.save(
                MetooEntity.builder().userInfo(userInfo).question(question).build()).getId();
    }

    @Override
    public void delete(Long metooId) {
        metooRepository.deleteById(metooId);
    }


}
