package com.example.demo.post.controller.response;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostResponseTest {

  @Test
  @DisplayName("Post로 응답을 생성할 수 있다.")
  void createPostResponseByPost() {
    // given
    Post post = Post.builder()
        .content("helloworld")
        .writer(User.builder()
            .id(1L)
            .email("kok22@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
            .build())
        .build();

    // when
    PostResponse postResponse = PostResponse.from(post);

    // then
    assertEquals("helloworld", postResponse.getContent());
    assertEquals(1L, postResponse.getWriter().getId());
    assertEquals("kok22@naver.com", postResponse.getWriter().getEmail());
    assertEquals("kok202", postResponse.getWriter().getNickname());
    assertEquals(UserStatus.ACTIVE, postResponse.getWriter().getStatus());
  }
}