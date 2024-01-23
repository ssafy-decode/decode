package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.domain.board.dto.ResponseCommentDto;
import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.domain.board.mapper.CommentMapper;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.CommentRepository;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import com.decode.web.entity.UserProfileEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AnswerRepository answerRepository;
    private final UserProfileRepository userProfileRepository;
    private final ResponseUserProfileMapper responseUserProfileMapper;

    @Override
    public Long save(CreateCommentDto createCommentDto) {

        // dto -> entity
        AnswerEntity answer = answerRepository.getReferenceById(
                createCommentDto.getAnswerId());
        UserProfileEntity userInfo = userProfileRepository.getReferenceById(
                createCommentDto.getUserId());
        CommentEntity comment = commentMapper.toEntity(createCommentDto);
        comment.setCommentWriter(userInfo);
        comment.setAnswer(answer);

        log.debug("Comment Entity : {}", comment);
        return commentRepository.save(comment).getId();
    }

    @Override
    public List<CommentEntity> findByAnswer(AnswerEntity answerEntity) {
        // 우선 정답쪽에서 관련 comment 들을 불러올 때 객체로 들어오는 것으로 구현
        return commentRepository.findAllByAnswer(answerEntity);
    }

    @Override
    public CommentEntity update(UpdateCommentDto updateCommentDto) {
        CommentEntity comment = commentRepository.findById(updateCommentDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment not found with id: " + updateCommentDto.getId()));
        // 기존의 comment content 내용 수정
        log.debug("Before Update Comment Entity : {}", comment);
        comment.setContent(updateCommentDto.getContent());
        log.debug("After Update Comment Entity : {}", comment);
        // 저장해서 db에 update 하기
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long commentId) {
        // 삭제하기
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<ResponseCommentDto> getResponseAnswerDtoList(AnswerEntity answerEntity) {
        List<CommentEntity> commentList = commentRepository.findAllByAnswer(answerEntity);

        return commentList.stream()
                .map(this::convertToResponseCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseCommentDto convertToResponseCommentDto(CommentEntity commentEntity) {

        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setCommentId(commentEntity.getId());
        responseCommentDto.setContent(commentEntity.getContent());
        responseCommentDto.setCreatedTime(commentEntity.getCreatedTime());
        responseCommentDto.setUpdatedTime(commentEntity.getUpdatedTime());

        UserProfileEntity commentWriterEntity = commentEntity.getCommentWriter();
        ResponseUserProfileDto commentWriterDto = responseUserProfileMapper.toDto(commentWriterEntity);
        responseCommentDto.setCommentWriter(commentWriterDto);

        return responseCommentDto;
    }


}

