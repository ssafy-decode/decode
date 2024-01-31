package com.decode.web.entity;

import com.decode.web.domain.board.dto.BoardProfileDto;
import com.decode.web.global.CommonEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "Question")
@NoArgsConstructor
public class QuestionEntity extends CommonEntity {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "question_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "question_writer_id")
    private UserProfileEntity questionWriter;

    @Setter
    @Column(name = "question_content")
    private String content;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<AnswerEntity> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<QuestionTagEntity> questionTags = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<MetooEntity> metoos = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<BookmarkEntity> bookmarks = new ArrayList<>();

    @Builder
    public QuestionEntity(Long id, String title, UserProfileEntity questionWriter, String content) {
        this.id = id;
        this.title = title;
        this.questionWriter = questionWriter;
        this.content = content;
    }

    public BoardProfileDto toDto() {
        return BoardProfileDto.builder()
                .questionId(id)
                .title(title)
                .build();
    }

}
