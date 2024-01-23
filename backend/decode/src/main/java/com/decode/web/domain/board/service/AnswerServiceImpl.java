package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseCommentDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.mapper.AnswerMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.board.repository.RecommendRepository;
import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.RecommendEntity;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final CommentService commentService;
    private final UserProfileMapper userProfileMapper;
    private final UserInfoRepository userInfoRepository;

    private final RecommendRepository recommendRepository;


    @Override
    public List<AnswerEntity> findAllByQuestion(QuestionEntity question) {

        return answerRepository.findAllByQuestion(question);
    }

    @Override
    public Long save(CreateAnswerDto createAnswerDto) {

        // dto -> entity
        AnswerEntity answer = answerMapper.toEntity(createAnswerDto);
        UserProfileEntity userProfile = userProfileRepository.getReferenceById(
                createAnswerDto.getUserId());
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
        List<ResponseCommentDto> responseCommentList = commentService.getResponseAnswerDtoList(
                answerEntity);
        responseAnswerDto.setCommentList(responseCommentList);

        UserProfileEntity answerWriterEntity = answerEntity.getAnswerWriter();
        UserProfileDto UserProfileEntity = userProfileMapper.toDto(answerWriterEntity);
        responseAnswerDto.setAnswerWriter(UserProfileEntity);

        return responseAnswerDto;
    }

    public Long recommend (Long answerId, RecommendDto recommendDto){
        // redis cache hit 조사
        // ...
        // 후 expire 5분 설정 후 끝나면 DB 저장
        // 우선 단순 API 구현

        //dto -> entity
        AnswerEntity answer = answerRepository.getReferenceById(answerId);
        UserInfoEntity userInfo = userInfoRepository.getReferenceById(recommendDto.getUserId());

        RecommendEntity recommend = RecommendEntity.builder().answer(answer).userInfo(userInfo)
                .recommend(
                        recommendDto.isRecommend()).build();
        recommendRepository.save(recommend);
        return null;
    }
}
