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
import org.mapstruct.control.MappingControl.Use;

@Getter
@Entity
@Table(name = "Comment")
@NoArgsConstructor
public class CommentEntity extends CommonEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @ManyToOne
    @JoinColumn(name = "comment_writer_id")
    private UserInfoEntity commentWriter;

    @Column(name = "comment_content")
    private String content;

    @Builder
    public CommentEntity(Long commentId, String content, UserInfoEntity commentWriter,
            AnswerEntity answer) {
        this.commentId = commentId;
        this.content = content;
        this.commentWriter = commentWriter;
        this.answer = answer;
    }
}
