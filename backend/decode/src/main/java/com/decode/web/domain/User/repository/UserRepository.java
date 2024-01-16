package com.decode.web.domain.User.repository;
import com.decode.web.domain.User.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
  @Override
  List<UserEntity> findAll();
  Optional<UserEntity> findById(Long id);

}
