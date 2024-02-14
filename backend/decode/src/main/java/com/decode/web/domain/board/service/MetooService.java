package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.domain.board.dto.MetooQuestionDto;
import com.decode.web.domain.board.dto.MetooQuestionIdDto;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface MetooService {

    Long save(MetooDto metooDto);

    void delete(Long questionId, Long userId) throws BadRequestException;

    List<MetooQuestionDto> get(Long userId) throws BadRequestException;

    List<Long> getIds(Long userId);
}
