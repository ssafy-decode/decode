package com.decode.web.domain.user.repository;

import com.decode.web.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {


    Optional<UserInfoEntity> findByEmail(String email);



}
