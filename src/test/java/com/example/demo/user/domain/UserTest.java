package com.example.demo.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  @DisplayName("User는 UserCreate로 객체로 생성할 수 있다")
  public void createUserByUserCreate() {
    // given
    UserCreate userCreate = UserCreate.builder()
        .email("kok202@kakao.com")
        .nickname("kok202")
        .address("Pangyo")
        .build();

    // when
    User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

    // then
    assertNull(user.getId());
    assertEquals("kok202@kakao.com", user.getEmail());
    assertEquals("kok202", user.getNickname());
    assertEquals("Pangyo", user.getAddress());
    assertEquals(UserStatus.PENDING, user.getStatus());
    assertEquals("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", user.getCertificationCode());
  }

  @Test
  @DisplayName("UserUpdate 객체로 업데이트 할 수 있다.")
  public void updateUserByUserUpdate() {
    // given
    User user = User.builder()
        .id(1L)
        .email("kok22@naver.com")
        .nickname("kok202")
        .address("Seoul")
        .status(UserStatus.ACTIVE)
        .lastLoginAt(100L)
        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        .build();


    UserUpdate userUpdate = UserUpdate.builder()
        .nickname("jsu123")
        .address("naRR")
        .build();

    // when
    user = user.update(userUpdate);

    // then
    assertEquals(1L, user.getId());
    assertEquals("kok22@naver.com", user.getEmail());
    assertEquals("jsu123", user.getNickname());
    assertEquals("naRR", user.getAddress());
    assertEquals(UserStatus.ACTIVE, user.getStatus());
    assertEquals("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", user.getCertificationCode());
  }

  @Test
  @DisplayName("User는 로그인을 할 수 있고 로그인 시 마지막 로그인 시간이 변경된다.")
  public void userLogin() {
    // given
    User user = User.builder()
        .id(1L)
        .email("kok22@naver.com")
        .nickname("kok202")
        .address("Seoul")
        .status(UserStatus.ACTIVE)
        .lastLoginAt(100L)
        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        .build();

    // when
    user = user.login(new TestClockHolder(1678530673958L));

    // then
    assertEquals(1678530673958L, user.getLastLoginAt());
  }

  @Test
  @DisplayName("User는 유효한 인증코드로 계정을 활성화 할 수 있다.")
  public void userCertificate() {
    // given
    User user = User.builder()
        .id(1L)
        .email("kok22@naver.com")
        .nickname("kok202")
        .address("Seoul")
        .status(UserStatus.PENDING)
        .lastLoginAt(100L)
        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        .build();

    // when
    user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    // then
    assertEquals(UserStatus.ACTIVE, user.getStatus());
  }

  @Test
  @DisplayName("User는 잘못된 인증코드로 계정을 활성화 하려하면 에러를 던진다.")
  public void userCertificateError() {
    // given
    User user = User.builder()
        .id(1L)
        .email("kok22@naver.com")
        .nickname("kok202")
        .address("Seoul")
        .status(UserStatus.PENDING)
        .lastLoginAt(100L)
        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        .build();

    // when
    // then
    assertThrows(CertificationCodeNotMatchedException.class, () -> user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"));
  }
}
