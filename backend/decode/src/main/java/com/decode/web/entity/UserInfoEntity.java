package com.decode.web.entity;

import com.decode.web.global.CommonEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Table(name = "UserInfo")
@NoArgsConstructor

public class UserInfoEntity extends CommonEntity {

    @OneToOne(mappedBy = "userInfoEntity")
    @PrimaryKeyJoinColumn
    private UserProfileEntity userProfileEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_name", unique = true)
    private String name;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_phone_number")
    private String phoneNumber;

    @Column(name = "user_birth")
    private String birth;


    @Builder
    public UserInfoEntity(Long id, String email, String password,
                          String phoneNumber, String birth, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.name = name;


    }
}
