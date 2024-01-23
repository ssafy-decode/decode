package com.decode.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "QuestionTag")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "tag_id")
    private Long tagId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Setter
    @Column(name = "version")
    private String version;
}
