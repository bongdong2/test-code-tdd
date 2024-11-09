package com.example.demo.post.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.mock.TestClockHolder;
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
    Post post = Post.from(writer, postCreate, new TestClockHolder(1678530673958L));

    // then
    assertEquals("helloworld", post.getContent());
    assertEquals(1678530673958L, post.getCreatedAt());
    assertEquals(1L, post.getWriter().getId());
    assertEquals("kok22@naver.com", post.getWriter().getEmail());
    assertEquals("kok202", post.getWriter().getNickname());
    assertEquals("Seoul", post.getWriter().getAddress());
    assertEquals(UserStatus.ACTIVE, post.getWriter().getStatus());
    assertEquals("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", post.getWriter().getCertificationCode());
  }

  @Test
  @DisplayName("PostUpdate로 게시물을 수정할 수 있다.")
  public void updatePostByPostCreate() {
    // given
    PostUpdate postUpdate = PostUpdate.builder()
            .content("foobar")
            .build();
    User writer = User.builder()
            .id(1L)
            .email("kok22@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
            .build();
    Post post = Post.builder()
            .id(1L)
            .content("helloworld")
            .writer(writer)
            .createdAt(1678530673958L)
            .modifiedAt(1678530673958L)
            .build();

    // when
    post = post.update(postUpdate, new TestClockHolder(1678530673958L));

    // then
    assertEquals("foobar", post.getContent());
    assertEquals(1678530673958L, post.getModifiedAt());
  }
}