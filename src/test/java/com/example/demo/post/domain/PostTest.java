package com.example.demo.post.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

  @Test
  @DisplayName("PostCreate로 게시물을 만들 수 있다.")
  public void createPostByPostCreate() {
    // given
    PostCreate postCreate = PostCreate.builder()
        .writerId(1)
        .content("helloworld")
        .build();

    User writer = User.builder()
        .id(1L)
        .email("kok22@naver.com")
        .nickname("kok202")
        .address("Seoul")
        .status(UserStatus.ACTIVE)
        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        .build();

    // when
    Post post = Post.from(writer, postCreate);

    // then
    assertEquals("helloworld", post.getContent());
    assertEquals(1L, post.getWriter().getId());
    assertEquals("kok22@naver.com", post.getWriter().getEmail());
    assertEquals("kok202", post.getWriter().getNickname());
    assertEquals("Seoul", post.getWriter().getAddress());
    assertEquals(UserStatus.ACTIVE, post.getWriter().getStatus());
    assertEquals("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", post.getWriter().getCertificationCode());
  }
}