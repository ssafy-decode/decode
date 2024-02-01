package com.decode.web.domain.elk;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ArticleDto {
    @Id
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private List<Long> questionTags;
    private LocalDateTime createdTime;
}
