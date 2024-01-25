package com.decode.web.domain.user.repository;

import com.decode.web.entity.UserInfoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {


    Optional<UserInfoEntity> findByEmail(String email);

    Optional<UserInfoEntity> findByNameAndPhoneNumberAndBirth(String name, String phoneNumber,
            String birth);


    Optional<UserInfoEntity> findByEmailAndNameAndPhoneNumberAndBirth(String email, String name,
            String phoneNumber, String birth);
}
