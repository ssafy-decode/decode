package com.decode.web.domain.user.repository;
import com.decode.web.entity.UserInfoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>{
  @Override
  List<UserInfoEntity> findAll();
  Optional<UserInfoEntity> findById(Long id);
  Optional<UserInfoEntity> findByEmail(String email);
  Optional<UserInfoEntity> findByNickname(String nickname);

}
