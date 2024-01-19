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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Comment")
@NoArgsConstructor
public class CommentEntity extends CommonEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @ManyToOne
    @JoinColumn(name = "comment_writer_id")
    private UserInfoEntity commentWriter;

    @Column(name = "comment_content")
    private String content;

    @Builder
    public CommentEntity(Long id, String content, UserInfoEntity commentWriter,
            AnswerEntity answer) {
        this.id = id;
        this.content = content;
        this.commentWriter = commentWriter;
        this.answer = answer;
    }
}
