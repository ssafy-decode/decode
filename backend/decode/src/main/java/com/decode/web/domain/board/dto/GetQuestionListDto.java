package com.decode.web.domain.board.dto;

import com.decode.web.entity.QuestionTagEntity;
import java.time.LocalDateTime;
import java.util.List;

public class GetQuestionListDto {
    private String title;
    private String writer;
    private int bookmarkCnt;
    private int metooCnt;
    private int answerCnt;
    private LocalDateTime createTime;
    private List<QuestionTagEntity> tags;
}
