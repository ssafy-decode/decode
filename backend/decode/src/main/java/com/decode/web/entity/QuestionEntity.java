package com.decode.web.entity;
import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Questions")
@NoArgsConstructor
public class QuestionEntity extends CommonEntity{

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_title")
    private String title;

    @Column(name = "user_id")
    private int writerId;

    @Column(name = "question_content")
    private String content;

    @Builder
    public QuestionEntity(Long id, String title, int writerId, String content){
        this.id = id;
        this.title = title;
        this.writerId = writerId;
        this.content = content;
    }
}
