package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.MetooEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetooServiceImpl implements MetooService {

    private final MetooRepository metooRepository;
    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;


    @Override
    public Long save(MetooDto metooDto) {
        // dto -> entity
        UserProfileEntity userProfile = userProfileRepository.findById(metooDto.getUserId())
                .orElseThrow(() -> new BadCredentialsException(
                        "User not found with id: " + metooDto.getUserId()));

        QuestionEntity question = questionRepository.findById(metooDto.getQuestionId()).orElseThrow(
                () -> new BadCredentialsException(
                        "Question not found with id: " + metooDto.getQuestionId()));

        return metooRepository.save(
                MetooEntity.builder().userProfile(userProfile).question(question).build()).getId();
    }

    @Override
    public void delete(Long meTooId) {
        MetooEntity metooEntity = metooRepository.findById(meTooId).orElseThrow(
                () -> new BadCredentialsException(
                        "meToo not found with id: " + meTooId));

        metooRepository.delete(metooEntity);
    }


}
