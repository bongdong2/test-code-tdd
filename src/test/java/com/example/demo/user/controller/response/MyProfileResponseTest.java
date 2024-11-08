package com.example.demo.user.controller.response;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyProfileResponseTest {

  @Test
  @DisplayName("User로 MyProfileResponse를 생성할 수 있다.")
  void createMyProfileResponseByUser() {
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
    UserResponse userResponse = UserResponse.from(user);

    // then
    assertEquals( 1, userResponse.getId());
    assertEquals("kok22@naver.com", userResponse.getEmail());
    assertEquals("kok202", userResponse.getNickname());
    assertEquals(UserStatus.ACTIVE, userResponse.getStatus());
    assertEquals(100L, userResponse.getLastLoginAt());
  }
}