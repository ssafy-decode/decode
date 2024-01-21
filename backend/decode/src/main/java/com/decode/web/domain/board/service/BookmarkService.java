package com.decode.web.domain.board.service;

public interface BookmarkService {
    Long bookMark(Long userId, Long questionId);

    void unBookMark(Long userId, Long questionId);
}
