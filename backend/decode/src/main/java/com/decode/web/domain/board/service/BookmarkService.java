package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import java.util.List;

public interface BookmarkService {

    Long bookMark(BookmarkDto bookmarkDto);

    void unBookMark(Long userId, Long questionId);

    List<QuestionListDto> getBookMarkQuetionList(Long userId);
}
