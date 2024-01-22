package com.decode.web.domain.board.repository;

import com.decode.web.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    BookmarkEntity findByUserProfileIdAndQuestionId(Long userId, Long questionId);
}
