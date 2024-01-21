package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseCommentDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.mapper.AnswerMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final CommentService commentService;
    private final UserProfileMapper userProfileMapper;

    @Autowired
    public AnswerServiceImpl(UserProfileRepository userProfileRepository,
            QuestionRepository questionRepository, AnswerRepository answerRepository,
            AnswerMapper answerMapper, CommentService commentService, UserProfileMapper userProfileMapper) {
        this.questionRepository = questionRepository;
        this.userProfileRepository = userProfileRepository;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.commentService = commentService;
        this.userProfileMapper = userProfileMapper;
    }


    @Override
    public List<AnswerEntity> findAllByQuestion(QuestionEntity question) {

        return answerRepository.findAllByQuestion(question);
    }

    @Override
    public Long save(CreateAnswerDto createAnswerDto) {

        // dto -> entity
        AnswerEntity answer = answerMapper.toEntity(createAnswerDto);
        UserProfileEntity userProfile = userProfileRepository.getReferenceById(createAnswerDto.getUserId());
        QuestionEntity question = questionRepository.getReferenceById(
                createAnswerDto.getQuestionId());
        answer.setAnswerWriter(userProfile);
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
    @Override
    public List<ResponseAnswerDto> getResponseAnswerDtoList(QuestionEntity questionEntity) {
        List<AnswerEntity> answerList = answerRepository.findAllByQuestion(questionEntity);

        return answerList.stream()
                .map(this::convertToResponseAnswerDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseAnswerDto convertToResponseAnswerDto(AnswerEntity answerEntity) {


        ResponseAnswerDto responseAnswerDto = new ResponseAnswerDto();
        responseAnswerDto.setAnswerId(answerEntity.getId());
        responseAnswerDto.setContent(answerEntity.getContent());
        responseAnswerDto.setAdopted(answerEntity.isAdopted());
        responseAnswerDto.setCreatedTime(answerEntity.getCreatedTime());
        responseAnswerDto.setUpdatedTime(answerEntity.getUpdatedTime());
        // CommentEntity를 가져와서 ResponseCommentDto로 변환
        List<ResponseCommentDto> responseCommentList = commentService.getResponseAnswerDtoList(answerEntity);
        responseAnswerDto.setCommentList(responseCommentList);

        UserProfileEntity answerWriterEntity = answerEntity.getAnswerWriter();
        UserProfileDto UserProfileEntity = userProfileMapper.toDto(answerWriterEntity);
        responseAnswerDto.setAnswerWriter(UserProfileEntity);

        return responseAnswerDto;
    }
}
