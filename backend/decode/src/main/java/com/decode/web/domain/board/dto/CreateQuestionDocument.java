package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.entity.QuestionEntity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
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
public class CreateQuestionDocument{
    @Id
    private Long id;
    private Long writerId;
    @Field(type = FieldType.Text, index = true, analyzer = "standard", searchAnalyzer = "standard")
    private String title;
    @Field(type = FieldType.Text, index = true, analyzer = "standard", searchAnalyzer = "standard")
    private String content;
    private List<QuestionTagDto> questionTags;

    public CreateQuestionDocument(QuestionEntity questionEntity, CreateQuestionDto questionDto){
        this.id = questionEntity.getId();
        this.writerId = questionDto.getQuestionWriterId();
        this.title = questionDto.getTitle();
        this.content = questionDto.getContent();
        this.questionTags = questionDto.getTags();
    }

}
