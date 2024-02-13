package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.MetooDto;
import org.apache.coyote.BadRequestException;

public interface MetooService {

    Long save(MetooDto metooDto);

    void delete(Long questionId, Long userId) throws BadRequestException;
}
