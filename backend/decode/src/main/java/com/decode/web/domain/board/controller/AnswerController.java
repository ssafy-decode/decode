package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.RecommendRepository;
import com.decode.web.domain.board.service.AnswerService;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.RecommendEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@Slf4j
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerRepository answerRepository;
    private final RecommendRepository recommendRepository;


    @PostMapping
    public ResponseDto save(@RequestBody CreateAnswerDto createAnswerDto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("createAnswerDto : {}", createAnswerDto.toString());
        if (!userId.equals(createAnswerDto.getUserId())) {
            log.info("writer : {}, user : {}", userId, createAnswerDto.getUserId());
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long answerId = answerService.save(createAnswerDto);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 등록 성공").data(answerId)
                .build();
    }

    @PatchMapping
    public ResponseDto update(UpdateAnswerDto updateAnswerDto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AnswerEntity answerEntity = answerRepository.findById(updateAnswerDto.getId()).orElseThrow(
                () -> new BadCredentialsException(
                        "Answer not found with id: " + updateAnswerDto.getId()));
        if (!userId.equals(answerEntity.getAnswerWriter().getId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        AnswerEntity answer = answerService.update(updateAnswerDto, answerEntity);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 업데이트 성공").data(answer)
                .build();
    }

    @DeleteMapping("/{answerId}")
    public ResponseDto delete(@PathVariable Long answerId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AnswerEntity answerEntity = answerRepository.findById(answerId).orElseThrow(
                () -> new BadCredentialsException(
                        "Answer not found with id: " + answerId));
        if (!userId.equals(answerEntity.getAnswerWriter().getId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        answerService.delete(answerEntity);
        return ResponseDto.builder().status(HttpStatus.OK).message("답변 삭제 성공").build();
    }

    @PostMapping("/recommend")
    public ResponseDto recommend(@RequestBody RecommendDto recommendDto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userId.equals(recommendDto.getUserId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long recommendId = answerService.recommend(recommendDto);
        return ResponseDto.builder().status(HttpStatus.OK).message(recommendId + "추천 등록 성공").build();
    }

    @DeleteMapping("/unrecommend/{answerId}")
    public ResponseDto unRecommend(@PathVariable Long answerId){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long deletedId = answerService.unRecommend(userId, answerId);
        return ResponseDto.builder().status(HttpStatus.OK).message(deletedId + "추천 취소 성공").build();
    }
}
