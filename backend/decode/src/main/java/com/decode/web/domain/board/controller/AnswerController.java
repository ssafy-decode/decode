package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.service.AnswerService;
import com.decode.web.domain.board.service.AnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerServiceImpl answerServiceImpl) {
        this.answerService = answerServiceImpl;
    }


}
