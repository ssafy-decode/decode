package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.mapper.AnswerMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserInfoEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final UserInfoRepository userInfoRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Autowired
    public AnswerServiceImpl(UserInfoRepository userInfoRepository,
            QuestionRepository questionRepository, AnswerRepository answerRepository,
            AnswerMapper answerMapper) {
        this.questionRepository = questionRepository;
        this.userInfoRepository = userInfoRepository;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }


    @Override
    public List<AnswerEntity> findAllByQuestion(QuestionEntity question) {

        return answerRepository.findAllByQuestion(question);
    }

    @Override
    public Long save(CreateAnswerDto createAnswerDto) {

        // dto -> entity
        AnswerEntity answer = answerMapper.toEntity(createAnswerDto);
        UserInfoEntity userInfo = userInfoRepository.getReferenceById(createAnswerDto.getUserId());
        QuestionEntity question = questionRepository.getReferenceById(
                createAnswerDto.getQuestionId());
        answer.setAnswerWriter(userInfo);
        answer.setQuestion(question);

        // save 후 id value 반환
        return answerRepository.save(answer).getId();
    }

    @Override
    public AnswerEntity update(UpdateAnswerDto updateAnswerDto) {

        // dto -> entity
        AnswerEntity answer = answerRepository.findById(updateAnswerDto.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Answer not found with id: " + updateAnswerDto.getId()));
        // 기존의 answer content 내용 수정
        log.debug("Before Update Answer Entity : {}", answer);
        answer.setContent(updateAnswerDto.getContent());
        log.debug("After Update Answer Entity : {}", answer);
        // 저장해서 db에 update 하기
        return answerRepository.save(answer);
    }

    @Override
    public void delete(Long answerId) {
        // 삭제하기
        answerRepository.deleteById(answerId);
    }
}
