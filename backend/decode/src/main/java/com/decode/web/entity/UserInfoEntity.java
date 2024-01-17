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
@Table(name = "UserInfo")
@NoArgsConstructor

public class UserInfoEntity extends CommonEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_nickname", unique = true)
    private String nickname;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_phone_number")
    private String phoneNumber;


    @Builder
    public UserInfoEntity(Long id, String email, String nickname, String password,
            String phoneNumber) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;

    }
}
