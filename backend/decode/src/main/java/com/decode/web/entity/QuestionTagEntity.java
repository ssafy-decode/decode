package com.decode.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "QuestionTag")
@NoArgsConstructor
public class QuestionTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    public QuestionTagEntity(Long id, TagEntity tag, QuestionEntity question) {
        this.id = id;
        this.tag = tag;
        this.question = question;
    }
}
