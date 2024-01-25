package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.service.TagService;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.QuestionTagEntity;
import com.decode.web.entity.UserProfileEntity;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserProfileRepository userProfileRepository;
    private final QuestionMapper questionMapper;
    private final TagService tagService;
    private final QuestionTagRepository questionTagRepository;
    private final AnswerRepository answerRepository;
    private final MetooRepository metooRepository;
    private final AnswerService answerService;
    private final ResponseUserProfileMapper responseUserProfileMapper;

    @Override
    public List<QuestionListDto> searchQuestionByKeyword(String keyword, List<Long> tagIds) {
        List<QuestionEntity> questionEntityList;
        if ("".equals(keyword)) {
            questionEntityList = questionRepository.findAllByOrderByCreatedTimeDesc();
        } else {
            questionEntityList = questionRepository.findByTitleContainingOrderByCreatedTimeDesc(
                    keyword);
        }
        List<QuestionListDto> questionListDtoList = new LinkedList<>(
                questionEntityList.stream()
                        .map(this::convertToDto)
                        .toList()
        );
        for (int i = questionListDtoList.size() - 1; i >= 0; i--) {
            for (long tag : tagIds) {
                if (!questionListDtoList.get(i).getTagList().contains(tag)) {
                    questionListDtoList.remove(i);
                    break;
                }
            }
        }
        return questionListDtoList;
    }

    private QuestionListDto convertToDto(QuestionEntity question) {
        return new QuestionListDto(
                question.getId(),
                question.getTitle(),
                responseUserProfileMapper.toDto(question.getQuestionWriter()),
                tagIds(question.getQuestionTags()),
                question.getCreatedTime(),
                question.getAnswers().size(),
                question.getMetoos().size()
        );
    }

    private List<Long> tagIds(List<QuestionTagEntity> questionTagEntityList) {
        return questionTagEntityList.stream()
                .map(QuestionTagEntity::getTagId)
                .collect(Collectors.toList());
    }


    @Override
    public Long createQuestion(CreateQuestionDto question) {
        UserProfileEntity questionWriter = userProfileRepository.getReferenceById(
                question.getQuestionWriterId());

        QuestionDto questionDto = new QuestionDto(question);
        questionDto.setQuestionWriter(questionWriter);
        QuestionEntity questionEntity = questionMapper.toEntity(questionDto);
        questionRepository.save(questionEntity);
        List<QuestionTagDto> tagList = question.getTags();
        for (QuestionTagDto questionTag : tagList) {
            questionTagRepository.save(QuestionTagEntity.builder().question(questionEntity)
                    .tagId(questionTag.getTagId()).version(questionTag.getVersion()).build());
        }
        return questionEntity.getId();
    }

    @Override
    public ResponseQuestionDto questionDetail(Long questionId) {
        QuestionEntity questionEntity = questionRepository.getReferenceById(questionId);
        UserProfileEntity writerEntity = questionEntity.getQuestionWriter();
        ResponseUserProfileDto writerDto = responseUserProfileMapper.toDto(writerEntity);
        String title = questionEntity.getTitle();
        String content = questionEntity.getContent();
        List<ResponseAnswerDto> answerList = answerService.getResponseAnswerDtoList(questionEntity);
        List<QuestionTagDto> questionTagDtoList = tagService.getQuestionTagList(questionId);
        int meTooCnt = metooRepository.countByQuestionId(questionEntity.getId());
        LocalDateTime createdTime = questionEntity.getCreatedTime();
        LocalDateTime updateTime = questionEntity.getUpdatedTime();

        return new ResponseQuestionDto(questionId, title, content, writerDto, questionTagDtoList,
                answerList, meTooCnt,
                createdTime, updateTime);
    }

    @Override
    public void deleteQuestion(Long questionId, QuestionEntity targetQuestion) {
        questionRepository.delete(targetQuestion);
    }

    @Override
    public ResponseQuestionDto updateQuestion(UpdateQuestionDto updateQuestion) {
        QuestionEntity question = questionRepository.getReferenceById(
                updateQuestion.getQuestionId());
        question.setTitle(updateQuestion.getTitle());
        question.setContent(updateQuestion.getContent());

        List<QuestionTagEntity> questionTagEntityList = questionTagRepository.findAllByQuestionId(
                updateQuestion.getQuestionId());
        questionTagRepository.deleteAll(questionTagEntityList);
        List<QuestionTagDto> tagList = updateQuestion.getTagList();

        for (QuestionTagDto questionTag : tagList) {
            questionTagRepository.save(QuestionTagEntity.builder().question(question)
                    .tagId(questionTag.getTagId()).version(questionTag.getVersion()).build());
        }

        return questionDetail(updateQuestion.getQuestionId());
    }
}
