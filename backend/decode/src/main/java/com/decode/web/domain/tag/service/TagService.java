package com.decode.web.domain.tag.service;

import com.decode.web.domain.board.dto.QuestionTagDto;
import com.decode.web.domain.tag.dto.TagDto;
import java.util.List;

public interface TagService {

    public boolean addTag(TagDto tagName);

    public List<QuestionTagDto> getQuestionTagList(Long questionId);

}
