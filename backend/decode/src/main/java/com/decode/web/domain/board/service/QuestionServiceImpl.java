package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.GetQuestionListDto;
import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.QuestionTagEntity;
import com.decode.web.entity.TagEntity;
import com.decode.web.entity.UserInfoEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;
    private final UserInfoRepository userInfoRepository;
    private final QuestionMapper questionMapper;
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;

    @Override
    public List<GetQuestionListDto> questionSearch(List<Integer> tags, String keyword) {
        return null;
    }

    @Override
    public String createQuestion(InputQuestionDto question) {
        UserInfoEntity questionWriter = userInfoRepository.getReferenceById(question.getQuestionWriterId());
        QuestionDto questionDto = new QuestionDto(question);
        questionDto.setQuestionWriter(questionWriter);
        QuestionEntity questionEntity = questionMapper.toEntity(questionDto);
        questionRepository.save(questionEntity);

        List<Long> tagList = question.getTags();
        for(long tagId : tagList){
            TagEntity tagEntity = tagRepository.getReferenceById(tagId);
            QuestionTagEntity questionTagEntity = new QuestionTagEntity(tagEntity, questionEntity);
            questionTagRepository.save(questionTagEntity);
        }
        return question.getTitle();
    }
}
