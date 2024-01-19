package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.QuestionTagEntity;
import com.decode.web.entity.TagEntity;
import com.decode.web.entity.UserInfoEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserInfoRepository userInfoRepository;
    private final QuestionMapper questionMapper;
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;

    @Override
    public List<QuestionListDto> searchQuestionByKeyword(String keyword) {
        List<QuestionEntity> questionEntities;
        if("".equals(keyword)) questionEntities = questionRepository.findAllByOrderByCreatedTimeDesc();
        else questionEntities = questionRepository.findByTitleContainingOrderByCreatedTimeDesc(keyword);
        return questionEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private QuestionListDto convertToDto(QuestionEntity question) {
        List<QuestionTagEntity> questionTagEntities = questionTagRepository.findAllByQuestionId(question.getId());
        return new QuestionListDto(
                question.getId(),
                question.getTitle(),
                question.getQuestionWriter().getNickname(), // 예시로 작성, 실제로는 UserInfoEntity에서 가져와야 함
                // tags는 어떻게 가져올지에 따라 수정 필요
                null,
                question.getCreatedTime(),
                // answerCnt, meTooCnt 값 가져오는 방법에 따라 수정 필요
                0,
                0
        );
    }
    @Override
    public String createQuestion(InputQuestionDto question) {
        UserInfoEntity questionWriter = userInfoRepository.getReferenceById(
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
}
