package com.decode.web.domain.tag.service;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.tag.dto.TagDto;
import java.util.List;

public interface TagService {

    public boolean addTag(TagDto tagName);

    List<Long> getQuestionTagIdList(Long questionId);

    public List<QuestionTagDto> getQuestionTagList(Long questionId);

}
