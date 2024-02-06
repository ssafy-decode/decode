package com.decode.web.domain.tag.service;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.dto.TagIdListDto;
import java.util.List;

public interface TagService {

    boolean addTag(TagDto tagName);

    List<Long> getQuestionTagIdList(Long questionId);

    List<QuestionTagDto> getQuestionTagList(Long questionId);

    TagIdListDto getTagListByUserId(Long userId);

}
