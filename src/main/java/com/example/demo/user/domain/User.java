package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import java.time.Clock;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Getter
public class User {
  private final Long id;
  private final String email;
  private final String nickname;
  private final String address;
  private final String certificationCode;
  private final UserStatus status;
  private final Long lastLoginAt;

  @Builder
  public User(Long id, String email, String nickname, String address, String certificationCode, UserStatus status, Long lastLoginAt) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.address = address;
    this.certificationCode = certificationCode;
    this.status = status;
    this.lastLoginAt = lastLoginAt;
  }

  public static User from(UserCreate userCreate) {
    return User.builder()
        .email(userCreate.getEmail())
        .nickname(userCreate.getNickname())
        .address(userCreate.getAddress())
        .status(UserStatus.PENDING)
        .certificationCode(UUID.randomUUID().toString())
        .build();
  }

  public User update(UserUpdate userUpdate) {
    return User.builder()
        .id(this.id)
        .email(this.email)
        .nickname(userUpdate.getNickname())
        .address(userUpdate.getAddress())
        .certificationCode(this.certificationCode)
        .status(status)
        .lastLoginAt(lastLoginAt)
        .build();
  }

  public User login() {
    return User.builder()
        .id(this.id)
        .email(this.email)
        .nickname(this.nickname)
        .address(this.address)
        .certificationCode(this.certificationCode)
        .status(status)
        .lastLoginAt(Clock.systemUTC().millis())
        .build();
  }

  public User certificate(String certificationCode) {
    if (!this.certificationCode.equals(certificationCode)) {
      throw new CertificationCodeNotMatchedException();
    }

    return User.builder()
        .id(this.id)
        .email(this.email)
        .nickname(this.nickname)
        .address(this.address)
        .certificationCode(this.certificationCode)
        .status(UserStatus.ACTIVE)
        .lastLoginAt(this.lastLoginAt)
        .build();
  }
}