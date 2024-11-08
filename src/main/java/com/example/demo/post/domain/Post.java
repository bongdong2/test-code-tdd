package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import java.time.Clock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

  private final Long id;
  private final String content;
  private final Long createdAt;
  private final Long modifiedAt;
  private final User writer;

  @Builder
  public Post(Long id, String content, Long createdAt, Long modifiedAt, User writer) {
    this.id = id;
    this.content = content;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.writer = writer;
  }

  public static Post from(User user, PostCreate postCreate) {
    return Post.builder()
        .content(postCreate.getContent())
        .writer(user)
        .createdAt(Clock.systemUTC().millis())
        .build();
  }

  public Post update(PostUpdate postUpdate) {
    return Post.builder()
        .id(this.id)
        .content(postUpdate.getContent())
        .createdAt(this.createdAt)
        .modifiedAt(Clock.systemUTC().millis())
        .writer(this.writer)
        .build();
  }
}
