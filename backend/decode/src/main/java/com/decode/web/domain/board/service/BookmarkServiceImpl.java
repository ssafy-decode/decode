package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.mapper.BookmarkMapper;
import com.decode.web.domain.board.repository.BookmarkRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.BookmarkEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{

    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;
    BookmarkRepository bookmarkRepository;
    @Override
    public Long bookMark(Long userId, Long questionId) {
        UserProfileEntity userProfile = userProfileRepository.getReferenceById(userId);
        QuestionEntity question = questionRepository.getReferenceById(questionId);

        return bookmarkRepository.save(
                BookmarkEntity.builder().userProfile(userProfile).question(question).build()).getId();
    }

    @Override
    public void unBookMark(Long userId, Long questionId) {
        bookmarkRepository.delete(bookmarkRepository.findByUserProfileIdAndQuestionId(userId,questionId));
    }
}
