package com.example.demo.mock;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

  // 싱글 스레드라서 굳이 이렇게 할 필요는 없을 것 같다.
  private final AtomicLong autoGeneratedId = new AtomicLong(0);
  private final List<User> data = Collections.synchronizedList(new ArrayList<>());

  @Override
  public Optional<User> findById(long id) {
    return data.stream().filter(user -> user.getId() == id).findFirst();
  }

  @Override
  public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
    return data.stream().filter(user -> user.getId() == id && user.getStatus() == userStatus).findFirst();
  }

  @Override
  public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
    return data.stream().filter(user -> user.getEmail().equals(email) && user.getStatus() == userStatus).findFirst();
  }

  @Override
  public User save(User user) {
    if(user.getId() == null || user.getId() == 0) {
      User newUser = User.builder()
          .id(autoGeneratedId.incrementAndGet())
          .email(user.getEmail())
          .nickname(user.getNickname())
          .address(user.getAddress())
          .certificationCode(user.getCertificationCode())
          .status(user.getStatus())
          .lastLoginAt(user.getLastLoginAt())
          .build();
      data.add(newUser);
      return newUser;
    } else {
      data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
      data.add(user);
      return user;
    }
  }
}