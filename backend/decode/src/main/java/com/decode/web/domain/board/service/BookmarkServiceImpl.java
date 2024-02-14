package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.dto.BookmarkQuestionDto;
import com.decode.web.domain.board.dto.QuestionDocument;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import com.decode.web.domain.board.repository.BookmarkRepository;
import com.decode.web.domain.board.repository.QuestionELKRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.BookmarkEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.UserProfileEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;
    private final BookmarkRepository bookmarkRepository;
    private final QuestionELKRepository questionELKRepository;
    private final ResponseUserProfileMapper responseUserProfileMapper;

    @Override
    public Long bookMark(BookmarkDto bookmarkDto) {
        Long userId = bookmarkDto.getUserId();
        Long questionId = bookmarkDto.getQuestionId();
        UserProfileEntity userProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new BadCredentialsException(
                        "User not found with id: " + userId));
        QuestionEntity question = questionRepository.findById(questionId).orElseThrow(
                () -> new BadCredentialsException(
                        "Question not found with id: " + questionId));

        return bookmarkRepository.save(
                        BookmarkEntity.builder().userProfile(userProfile).question(question).build())
                .getId();
    }

    @Override
    public void unBookMark(Long userId, Long questionId) throws BadCredentialsException {
        BookmarkEntity bookmarkEntity = bookmarkRepository.findByUserProfileIdAndQuestionId(userId,
                questionId);
        if (bookmarkEntity == null) {
            throw new BadCredentialsException("유효하지 않은 북마크");
        }
        bookmarkRepository.delete(
                bookmarkRepository.findByUserProfileIdAndQuestionId(userId, questionId));
    }

    @Override
    public List<ResponseQuestionListDto> getBookMarkQuestionList(Long userId) {
        UserProfileEntity userProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with ID: " + userId));

        List<BookmarkEntity> bookmarks = bookmarkRepository.findByUserProfile(userProfile);

        return bookmarks.stream()
                .map(this::convertBookmarkToResponseQuestionListDto)
                .toList();
    }

    @Override
    public List<Long> get(Long userId) {
        List<Long> list = new ArrayList<>();
        bookmarkRepository.findAllByUserProfileId(userId)
                .forEach(e -> list.add(e.getQuestion().getId()));
        return list;
    }

    public ResponseQuestionListDto convertBookmarkToResponseQuestionListDto(
            BookmarkEntity bookmark) {
        QuestionEntity questionEntity = bookmark.getQuestion();
        QuestionDocument questionDocument = questionELKRepository.findById(questionEntity.getId())
                .orElseThrow(
                        () -> new BadCredentialsException(
                                "Question not found with id: " + questionEntity.getId()));
        return new ResponseQuestionListDto(
                questionEntity.getId(),
                questionDocument.getTitle(),
                responseUserProfileMapper.toDto(
                        userProfileRepository.getReferenceById(questionDocument.getWriterId())),
                questionDocument.getQuestionTags(),
                questionEntity.getCreatedTime(),
                questionEntity.getUpdatedTime(),
                questionEntity.getAnswers().size(),
                questionEntity.getMetoos().size(),
                questionEntity.getBookmarks().size()
        );
    }
}
