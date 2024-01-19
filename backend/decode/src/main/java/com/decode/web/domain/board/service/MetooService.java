package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.MetooDto;

public interface MetooService {

    Long save(MetooDto metooDto);

    void delete(Long metooId);


}
