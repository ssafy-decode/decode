package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.AnswerCountResponseDto;
import com.decode.web.domain.board.dto.AnswerDoAdoptDto;
import com.decode.web.domain.board.dto.AnswerSomethingDto;
import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.service.AnswerService;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.service.PointService;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.global.ResponseDto;
import java.util.List;
import javax.security.auth.login.CredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@Slf4j
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerRepository answerRepository;
    private final PointService pointService;


    @PostMapping
    public ResponseDto save(@RequestBody CreateAnswerDto createAnswerDto, Authentication auth)
            throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        log.info("createAnswerDto : {}", createAnswerDto.toString());
        if (!userId.equals(createAnswerDto.getUserId())) {
            throw new CredentialException("사용자 불일치");
        }
        Long answerId = answerService.save(createAnswerDto);
        pointService.updateUserPointAndExp(userId, Point.ANSWER);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("답변 등록 성공")
                .data(answerId)
                .build();
    }

    @PatchMapping
    public ResponseDto update(UpdateAnswerDto updateAnswerDto, Authentication auth)
            throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        AnswerEntity answerEntity = answerRepository.findById(updateAnswerDto.getId()).orElseThrow(
                () -> new BadCredentialsException(
                        "Answer not found with id: " + updateAnswerDto.getId()));
        if (!userId.equals(answerEntity.getAnswerWriter().getId())) {
            throw new CredentialException("사용자 불일치");
        }
        AnswerEntity answer = answerService.update(updateAnswerDto, answerEntity);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("답변 업데이트 성공")
                .data(answer)
                .build();
    }

    @DeleteMapping("/{answerId}")
    public ResponseDto delete(@PathVariable Long answerId, Authentication auth)
            throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        AnswerEntity answerEntity = answerRepository.findById(answerId).orElseThrow(
                () -> new BadCredentialsException(
                        "Answer not found with id: " + answerId));
        if (!userId.equals(answerEntity.getAnswerWriter().getId())) {
            throw new CredentialException("사용자 불일치");
        }
        answerService.delete(answerEntity);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("답변 삭제 성공")
                .data("")
                .build();
    }

    @PostMapping("/recommend")
    public ResponseDto recommend(@RequestBody RecommendDto recommendDto, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        if (!userId.equals(recommendDto.getUserId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long recommendId = answerService.recommend(recommendDto);
        return ResponseDto.builder().status(HttpStatus.OK).message(recommendId + "추천 등록 성공")
                .build();
    }

    @DeleteMapping("/unrecommend/{answerId}")
    public ResponseDto unRecommend(@PathVariable Long answerId, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        Long deletedId = answerService.unRecommend(userId, answerId);
        return ResponseDto.builder().status(HttpStatus.OK).message(deletedId + "추천 취소 성공").build();
    }

    @GetMapping("/list/{userId}")
    public ResponseDto getAnswerListByUserId(@PathVariable Long userId) {
        BoardProfileResponseDto data = answerService.findAllByUserId(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("질문 목록 조회 완료")
                .data(data)
                .build();
    }

    @PostMapping("/adopt")
    public ResponseDto doAdopt(@RequestBody AnswerDoAdoptDto answerDoAdoptDto,
            Authentication auth) throws CredentialException {
        Long authUserId = (Long) auth.getPrincipal();
        if (!answerDoAdoptDto.getUserId().equals(authUserId)) {
            throw new CredentialException("사용자 불일치");
        }
        answerService.doAdopt(answerDoAdoptDto.getUserId(), answerDoAdoptDto.getAnswerId());
        answerService.sendEmailToSubscriber(answerDoAdoptDto.getAnswerId());
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("답변 채택 완료")
                .data("")
                .build();
    }

    @GetMapping("/count/{userId}")
    public ResponseDto getAnswerCount(@PathVariable Long userId) {
        AnswerCountResponseDto data = answerService.getAnswerCountByUserId(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("총 답변 채택 수 가져오기 성공")
                .data(data)
                .build();
    }

    @GetMapping("/recommend/{userId}")
    public ResponseDto getRecommendAnswersByUserId(@PathVariable Long userId)
            throws BadRequestException {
        List<AnswerSomethingDto> data = answerService.getRecommendAnswersByUserId(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("유저가 추천한 답변 목록 조회")
                .data(data)
                .build();
    }

    @GetMapping("/adopt/{questionId}")
    public ResponseDto getAdoptAnswersByUserId(@PathVariable Long questionId)
            throws BadRequestException {
        List<Long> data = answerService.getAdoptAnswersByUserId(questionId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("유저가 채택한 답변 목록 조회")
                .data(data)
                .build();
    }

}
