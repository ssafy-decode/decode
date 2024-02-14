package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.dto.BookmarkQuestionDto;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import java.util.List;

public interface BookmarkService {

    Long bookMark(BookmarkDto bookmarkDto);

    void unBookMark(Long userId, Long questionId);

    List<ResponseQuestionListDto> getBookMarkQuestionList(Long userId);

    List<Long> get(Long userId);
}
