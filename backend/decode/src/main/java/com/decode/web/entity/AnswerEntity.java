package com.decode.web.entity;

import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "Answer")
@NoArgsConstructor
public class AnswerEntity extends CommonEntity {

    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_writer_id")
    @Setter
    private UserInfoEntity answerWriter;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @Setter
    private QuestionEntity question;

    @Setter
    @Column(name = "answer_content")
    private String content;

    @Setter
    @Column(name = "is_adopted")
    private boolean idAdopted;

    public AnswerEntity(Long id, UserInfoEntity answerWriter, QuestionEntity question,
            String content, boolean idAdopted) {
        this.id = id;
        this.answerWriter = answerWriter;
        this.question = question;
        this.content = content;
        this.idAdopted = idAdopted;
    }
}
