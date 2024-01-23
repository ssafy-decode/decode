package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BookmarkDto;

public interface BookmarkService {

    Long bookMark(BookmarkDto bookmarkDto);

    void unBookMark(Long userId, Long questionId);
}
