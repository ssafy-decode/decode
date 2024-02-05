package com.decode.web.domain.tag.service;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.dto.TagIdListDto;
import com.decode.web.domain.tag.mapper.TagMapper;
import com.decode.web.domain.tag.repository.QuestionTagRepository;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.domain.tag.repository.UserTagRepository;
import com.decode.web.entity.QuestionTagEntity;
import com.decode.web.entity.TagEntity;
import com.decode.web.entity.UserTagEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final QuestionTagRepository questionTagRepository;
    private final UserTagRepository userTagRepository;


    @Override
    public boolean addTag(TagDto tagName) {
        TagEntity tagEntity = tagMapper.toEntity(tagName);
        try {
            tagRepository.save(tagEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Long> getQuestionTagIdList(Long questionId) {
        List<QuestionTagEntity> questionTagEntityList = questionTagRepository.findAllByQuestionId(
                questionId);
        return questionTagEntityList.stream()
                .map(this::convertQuestionTagEntityToTagId)
                .collect(Collectors.toList());
    }

    public TagIdListDto getTagListByUserId(Long userId) {
        List<UserTagEntity> userTagEntities = userTagRepository.findAllByUserProfileId(userId);
        return TagIdListDto.builder()
                .tagIdList(userTagEntities.stream()
                        .map(UserTagEntity::getTagId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Long convertQuestionTagEntityToTagId(QuestionTagEntity questionTagEntity) {
        return questionTagEntity.getTagId();
    }

    @Override
    public List<QuestionTagDto> getQuestionTagList(Long questionId) {
        List<QuestionTagEntity> questionTagEntityList = questionTagRepository.findAllByQuestionId(
                questionId);

        return questionTagEntityList.stream().map(this::convertQuestionEntityToQuestionTagDto)
                .collect(Collectors.toList());
    }

    public QuestionTagDto convertQuestionEntityToQuestionTagDto(
            QuestionTagEntity questionTagEntity) {
        return new QuestionTagDto(questionTagEntity.getTagId(), questionTagEntity.getVersion());
    }
}
