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
@Table(name = "Recommend")
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
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfoEntity userInfo;

    @Column(name = "recommend")
    private boolean recommend;
}
