package com.decode.web.entity;

import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Follow")
@NoArgsConstructor
public class FollowEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserProfileEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserProfileEntity toUser;

    @Builder
    public FollowEntity(UserProfileEntity fromUser, UserProfileEntity toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
