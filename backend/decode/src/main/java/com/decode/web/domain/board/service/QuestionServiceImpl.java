package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BoardProfileDto;
import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionDocument;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.domain.board.repository.BookmarkRepository;
import com.decode.web.domain.board.repository.MetooRepository;
import com.decode.web.domain.board.repository.QuestionELKRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserProfileEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final AnswerService answerService;
    private final ResponseUserProfileMapper responseUserProfileMapper;
    private final QuestionELKRepository questionELKRepository;
    private final MetooRepository metooRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<ResponseQuestionListDto> searchQuestionByKeyword(String keyword,
            List<Long> tagIds) {
        List<QuestionDocument> questionDocumentList;
        if (keyword.isEmpty()) {
            if (tagIds.isEmpty()) {
                questionDocumentList = questionELKRepository.findAllByOrderByIdDesc();
            } else {
                questionDocumentList = questionELKRepository.findByQuestionTags(tagIds);
            }
        } else {
            if (tagIds.isEmpty()) {
                questionDocumentList = questionELKRepository.findByTitleOrContent(keyword);
            } else {
                questionDocumentList = questionELKRepository.findByQuestionTagsAndTitleAndContent(
                        keyword, tagIds);
            }
        }

        return questionDocumentList.stream()
                .map(this::convertDocumentToQuestionListDto)
                .collect(Collectors.toList());

    }

    private ResponseQuestionListDto convertDocumentToQuestionListDto(
            QuestionDocument questionDocument) {
        QuestionEntity questionEntity = questionRepository.getReferenceById(
                questionDocument.getId());
        return new ResponseQuestionListDto(
                questionDocument.getId(),
                questionDocument.getTitle(),
                responseUserProfileMapper.toDto(
                        userProfileRepository.getReferenceById(questionDocument.getWriterId())),
                questionDocument.getQuestionTags(),
                questionEntity.getCreatedTime(),
                questionEntity.getUpdatedTime(),
                questionEntity.getAnswers().size(),
                questionEntity.getMetoos().size(),
                questionEntity.getBookmarks().size());
    }

    @Override
    @Transactional
    public Long createQuestion(CreateQuestionDto question) {
        Long questionId = questionRepository.save(QuestionEntity.builder().build()).getId();
        QuestionDocument createQuestionDocument = new QuestionDocument(questionId, question);
        log.info("QuestionDocument: {}", createQuestionDocument);
        return questionELKRepository.save(createQuestionDocument).getId();
    }

    @Override
    @Transactional
    public ResponseQuestionDto questionDetail(Long questionId) {
        QuestionEntity questionEntity = questionRepository.findById(questionId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Question not found with id at DB: " + questionId));
        QuestionDocument questionDocument = questionELKRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Question not found with id at ELK: " + questionId));
        UserProfileEntity writerEntity = userProfileRepository.findById(
                        questionDocument.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + questionDocument.getWriterId()));
        ResponseUserProfileDto writerDto = responseUserProfileMapper.toDto(writerEntity);
        String title = questionDocument.getTitle();
        String content = questionDocument.getContent();
        List<ResponseAnswerDto> answerList = answerService.getResponseAnswerDtoList(questionEntity);
        List<QuestionTagDto> questionTagDtoList = questionDocument.getQuestionTags();
        int meTooCnt = questionEntity.getMetoos().size();
        LocalDateTime createdTime = questionEntity.getCreatedTime();
        LocalDateTime updateTime = questionEntity.getUpdatedTime();

        return new ResponseQuestionDto(questionId, title, content, writerDto, questionTagDtoList,
                answerList, meTooCnt, createdTime, updateTime,
                questionEntity.getBookmarks().size());
    }

    @Override
    public void deleteQuestion(QuestionDocument questionDocument) {
        QuestionEntity targetQuestion = questionRepository.findById(questionDocument.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Question not found with id: " + questionDocument.getId()));
        questionRepository.delete(targetQuestion);
        questionELKRepository.delete(questionDocument);
    }

    @Override
    @Transactional
    public ResponseQuestionDto updateQuestion(UpdateQuestionDto updateQuestion) {
        QuestionDocument questionDocument = questionELKRepository.findById(
                        updateQuestion.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Question not found with id: " + updateQuestion.getQuestionId()));

        questionDocument.setTitle(updateQuestion.getTitle());
        questionDocument.setContent(updateQuestion.getContent());
        questionDocument.setQuestionTags(updateQuestion.getTagList());

        questionRepository.save(
                questionRepository.getReferenceById(updateQuestion.getQuestionId()));

        questionELKRepository.save(questionDocument);

        return questionDetail(questionDocument.getId());
    }

    @Override
    public BoardProfileResponseDto findAllByUserId(Long userId) {
        List<BoardProfileDto> questions = questionELKRepository.findAllByWriterId(userId).stream()
                .map(this::convertDocumentToBoardProfileDto).toList();
        return BoardProfileResponseDto.builder().list(questions).size(questions.size()).build();
    }

    public BoardProfileDto convertDocumentToBoardProfileDto(QuestionDocument document) {
        return BoardProfileDto.builder()
                .title(document.getTitle())
                .content(document.getContent())
                .questionId(document.getId())
                .build();
    }

}
