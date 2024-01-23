package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.QuestionTagDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.mapper.QuestionTagMapper;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.domain.tag.service.TagService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserProfileRepository userProfileRepository;
    private final QuestionMapper questionMapper;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final QuestionTagRepository questionTagRepository;
    private final QuestionTagMapper questionTagMapper;
    private final AnswerRepository answerRepository;
    private final MetooRepository metooRepository;
    private final AnswerService answerService;
    private final UserProfileMapper userProfileMapper;

    @Override
    public List<QuestionListDto> searchQuestionByKeyword(String keyword, List<Long> tagIds) {
        List<QuestionEntity> questionEntityList;
        if ("".equals(keyword)) {
            questionEntityList = questionRepository.findAllByOrderByCreatedTimeDesc();
        } else {
            questionEntityList = questionRepository.findByTitleContainingOrderByCreatedTimeDesc(
                    keyword);
        }
        return questionEntityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private QuestionListDto convertToDto(QuestionEntity question) {
//        List<QuestionTagEntity> questionTagEntities = questionTagRepository.findAllByQuestionId(question.getId());
        int answerCnt = answerRepository.countByQuestionId(question.getId());
        List<QuestionTagDto> questionTagDtoList = tagService.getQuestionTagList(question.getId());
        return new QuestionListDto(
                question.getId(),
                question.getTitle(),
                userProfileMapper.toDto(question.getQuestionWriter()),
                questionTagDtoList,
                question.getCreatedTime(),
                // answerCnt, meTooCnt 값 가져오는 방법에 따라 수정 필요
                answerCnt,
                0
        );
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
            TagEntity tagEntity = tagRepository.getReferenceById(questionTag.getTagId());
            QuestionTagEntity questionTagEntity = new QuestionTagEntity();
            questionTagEntity.setQuestion(questionEntity);
            questionTagEntity.setTag(tagEntity);
            questionTagEntity.setVersion(questionTag.getVersion());
            questionTagRepository.save(questionTagEntity);
        }
        return questionEntity.getId();
    }

    @Override
    public ResponseQuestionDto questionDetail(Long questionId) {
        QuestionEntity questionEntity = questionRepository.getReferenceById(questionId);
        UserProfileEntity writerEntity = questionEntity.getQuestionWriter();
        UserProfileDto writerDto = userProfileMapper.toDto(writerEntity);
        String title = questionEntity.getTitle();
        String content = questionEntity.getContent();
        List<ResponseAnswerDto> answerList = answerService.getResponseAnswerDtoList(questionEntity);
        List<QuestionTagDto> questionTagDtoList = tagService.getQuestionTagList(questionId);
        Long meTooCnt = metooRepository.countAllByQuestion(questionEntity);
        LocalDateTime createdTime = questionEntity.getCreatedTime();
        LocalDateTime updateTime = questionEntity.getUpdatedTime();

        return new ResponseQuestionDto(questionId, title, content, writerDto, questionTagDtoList,
                answerList, meTooCnt,
                createdTime, updateTime);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        QuestionEntity questionEntity = questionRepository.getReferenceById(questionId);
        questionRepository.delete(questionEntity);
    }

    @Override
    public Long updateQuestion(UpdateQuestionDto updateQuestion) {
        QuestionEntity question = questionRepository.getReferenceById(
                updateQuestion.getQuestionId());
        question.setTitle(updateQuestion.getTitle());
        question.setContent(updateQuestion.getContent());

        List<QuestionTagEntity> questionTagEntityList = questionTagRepository.findAllByQuestionId(
                updateQuestion.getQuestionId());
        questionTagRepository.deleteAll(questionTagEntityList);
        List<QuestionTagDto> tagList = updateQuestion.getTagList();

        for (QuestionTagDto questionTag : tagList) {
            TagEntity tagEntity = tagRepository.getReferenceById(questionTag.getTagId());
            QuestionTagEntity questionTagEntity = new QuestionTagEntity();
            questionTagEntity.setQuestion(question);
            questionTagEntity.setTag(tagEntity);
            questionTagEntity.setVersion(questionTag.getVersion());
            questionTagRepository.save(questionTagEntity);
        }

        return updateQuestion.getQuestionId();
    }
}
