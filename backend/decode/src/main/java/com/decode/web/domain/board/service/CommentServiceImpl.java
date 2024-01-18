package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.domain.board.repository.CommentRepository;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Long save(CommentEntity commentEntity){
        return commentRepository.save(commentEntity).getId();
    }
    @Override
    public List<CommentEntity> findByAnswer(AnswerEntity answerEntity){
        return commentRepository.findAllByAnswer(answerEntity);
    }

//    @Override
//    public List<CommentEntity> findByAnswerId(String answerId){
//        return commentRepository.findAllByAnswerId(answerId);
//    }

    @Override
    public CommentEntity update(UpdateCommentDto updateCommentDto){
//        Optional<CommentEntity> optionalComment = commentRepository.findById(updateCommentDto);
//        if(optionalComment.isPresent()){
//            CommentEntity commentEntity = optionalComment.get();
//            commentEntity.setCommentContent(updateCommentDto.get);
//            commentRepository.save(commentEntity);
//        }else{
//            // 업데이트 대상이 존재하지 않음.
//            // error 처리는 어떻게 할 것인가?
//        }
        return null;
    }

    @Override
    public void delete(Long commentId){
        // 1. 삭제 처리는 실패하면 뭐가
        // 2. 객체로 갈 지 아이디로 갈 지
        commentRepository.deleteById(commentId);
    }

}

