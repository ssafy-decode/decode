package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.AnswerCountResponseDto;
import com.decode.web.domain.board.dto.AnswerSomethingDto;
import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.exception.InvalidWriterException;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface AnswerService {

    Long save(CreateAnswerDto createAnswerDto);

    AnswerEntity update(UpdateAnswerDto updateAnswerDto, AnswerEntity answerEntity);

    void delete(AnswerEntity answer);

    List<ResponseAnswerDto> getResponseAnswerDtoList(QuestionEntity questionEntity);

    ResponseAnswerDto convertToResponseAnswerDto(AnswerEntity answerEntity);

    Long recommend(RecommendDto recommendDto);

    Long unRecommend(Long userId, Long answerId);

    BoardProfileResponseDto findAllByUserId(Long userId);

    void doAdopt(Long userId, Long answerId) throws InvalidWriterException;

    AnswerCountResponseDto getAnswerCountByUserId(Long userId);

    void sendEmailToSubscriber(Long answerId);

    List<AnswerSomethingDto> getRecommendAnswersByUserId(Long userId)
            throws BadRequestException;

    List<Long> getAdoptAnswersByUserId(Long userId) throws BadRequestException;
}
