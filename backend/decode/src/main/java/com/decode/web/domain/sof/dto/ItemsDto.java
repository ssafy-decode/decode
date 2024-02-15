package com.decode.web.domain.sof.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDto {
    private String post_state;
    private Boolean is_answered;
    private Integer view_count;
    private Long accepted_answer_id;
    private Integer answer_count;

    private Integer score;
    private String link;
    private String title;

}
