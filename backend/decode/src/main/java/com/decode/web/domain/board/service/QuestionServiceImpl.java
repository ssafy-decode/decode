package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.QuestionTagEntity;
import com.decode.web.entity.TagEntity;
import com.decode.web.entity.UserProfileEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserProfileRepository userProfileRepository;
    private final QuestionMapper questionMapper;
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;
    private final AnswerRepository answerRepository;
    private final MetooRepository metooRepository;
    private final AnswerService answerService;
    private final UserProfileMapper userProfileMapper;

    @Override
    public List<QuestionListDto> searchQuestionByKeyword(String keyword) {
        List<QuestionEntity> questionEntities;
        if ("".equals(keyword)) {
            questionEntities = questionRepository.findAllByOrderByCreatedTimeDesc();
        } else {
            questionEntities = questionRepository.findByTitleContainingOrderByCreatedTimeDesc(
                    keyword);
        }
        return questionEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private QuestionListDto convertToDto(QuestionEntity question) {
//        List<QuestionTagEntity> questionTagEntities = questionTagRepository.findAllByQuestionId(question.getId());
        int answerCnt = answerRepository.countByQuestion_Id(question.getId());
        return new QuestionListDto(
                question.getId(),
                question.getTitle(),
                userProfileMapper.toDto(question.getQuestionWriter()),
                // tags는 어떻게 가져올지에 따라 수정 필요
                null,
                question.getCreatedTime(),
                // answerCnt, meTooCnt 값 가져오는 방법에 따라 수정 필요
                answerCnt,
                0
        );
    }

    @Override
    public String createQuestion(InputQuestionDto question) {
        UserProfileEntity questionWriter = userProfileRepository.getReferenceById(
                question.getQuestionWriterId());
        QuestionDto questionDto = new QuestionDto(question);
        questionDto.setQuestionWriter(questionWriter);
        QuestionEntity questionEntity = questionMapper.toEntity(questionDto);
        questionRepository.save(questionEntity);

        List<Long> tagList = question.getTags();
        for (long tagId : tagList) {
            TagEntity tagEntity = tagRepository.getReferenceById(tagId);
            QuestionTagEntity questionTagEntity = new QuestionTagEntity(tagEntity, questionEntity);
            questionTagRepository.save(questionTagEntity);
        }
        return question.getTitle();
    }

    @Override
    public ResponseQuestionDto questionDetail(Long questionId) {
        QuestionEntity questionEntity = questionRepository.getReferenceById(questionId);
        UserProfileEntity writerEntity = questionEntity.getQuestionWriter();
        UserProfileDto writerDto = userProfileMapper.toDto(writerEntity);
        String title = questionEntity.getTitle();
        String content = questionEntity.getContent();
        List<ResponseAnswerDto> answerList = answerService.getResponseAnswerDtoList(questionEntity);
        Long meTooCnt = metooRepository.countAllByQuestion(questionEntity);
        LocalDateTime createdTime = questionEntity.getCreatedTime();
        LocalDateTime updateTime = questionEntity.getUpdatedTime();

        return new ResponseQuestionDto(questionId, title, content, writerDto, answerList, meTooCnt,
                createdTime, updateTime);
    }
}
