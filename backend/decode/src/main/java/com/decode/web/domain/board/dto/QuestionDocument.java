package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "question")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;
    private Long writerId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String content;
    private List<QuestionTagDto> questionTags;

//    @CreatedDate
//    @Field(type = FieldType.Date)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Field(type = FieldType.Date)
//    private LocalDateTime updatedAt;

    public QuestionDocument(Long questionId, CreateQuestionDto questionDto) {
        this.id = questionId;
        this.writerId = questionDto.getQuestionWriterId();
        this.title = questionDto.getTitle();
        this.content = questionDto.getContent();
        this.questionTags = questionDto.getTags();
    }

}
