package com.example.demo.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CertificationServiceTest {

  @Test
  @DisplayName("이메일과 컨텐츠가 제대로 만들어져서 보내지는지 테스트한다")
  public void sendEmailAndContents() {
    // given
    FakeMailSender fakeMailSender = new FakeMailSender();
    CertificationService certificationService = new CertificationService(fakeMailSender);

    // when
    certificationService.send("seungui821@ohcompany.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    // then
    assertThat(fakeMailSender.email).isEqualTo("seungui821@ohcompany.com");
    assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
    assertThat(fakeMailSender.content).isEqualTo(
        "Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
  }
}