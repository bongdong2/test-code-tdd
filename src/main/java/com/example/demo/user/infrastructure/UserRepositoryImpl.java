package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public Optional<User> findById(long id) {
    return userJpaRepository.findById(id).map(UserEntity::toModel);
  }

  @Override
  public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
    return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toModel);
  }

  @Override
  public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
    return userJpaRepository.findByEmailAndStatus(email, userStatus).map(UserEntity::toModel);
  }

  @Override
  public User save(User user) { // 저장하는 것은 반대로 도메인 객체를 영속성 객체로 바꿔주는 메시지가 필요
    return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
  }
}
