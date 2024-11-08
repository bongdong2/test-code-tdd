package com.example.demo.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  @DisplayName("User는 UserCreate로 객체로 생성할 수 있다")
  public void createUserByUserCreate() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("UserUpdate 객체로 User 업데이트")
  public void updateUserByUserUpdate() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("User는 로그인을 할 수 있고 로그인 시 마지막 로그인 시간이 변경된다.")
  public void userLogin() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("User는 유효한 인증코드로 계정을 활성화 할 수 있다.")
  public void userCertificate() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("User는 잘못된 인증코드로 계정을 활성화 하려하면 에러를 던진다.")
  public void userCertificateError() {
    // given

    // when

    // then
  }
}
