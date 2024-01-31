package com.decode.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Table(
        name = "Recommend",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UniqueUserAndAnswer",
                        columnNames = {
                                "answer_id",
                                "user_id"
                        }
                ),
        }
)
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class RecommendEntity {

    @Id
    @Column(name = "recommend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfileEntity userProfile;
}
