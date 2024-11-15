package com.example.demo.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.userService = UserService.builder()
            .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
            .clockHolder(new TestClockHolder(1678530673958L))
            .userRepository(fakeUserRepository)
            .certificationService(new CertificationService(fakeMailSender))
            .build();

        fakeUserRepository.save(User.builder()
            .id(1L)
            .email("kok202@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(0L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
            .build());

        fakeUserRepository.save(User.builder()
            .id(2L)
            .email("kok303@naver.com")
            .nickname("kok303")
            .address("Busan")
            .status(UserStatus.PENDING)
            .lastLoginAt(0L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build());
    }

    @Test
    @DisplayName("getByEmail은 ACTIVE 상태인 유저를 찾아올 수 있다")
    void getActiveUserByEmail() {
        // given
        String email = "kok202@naver.com";

        // when
        User result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("kok202");
    }

    @Test
    @DisplayName("getByEmail은 PENDING 상태인 유저는 찾아올 수 없다")
    void getPendingUserByEmail() {
        // given
        String email = "kok303@naver.com";

        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("getById는 ACTIVE 상태인 유저를 찾아올 수 있다")
    void getActiveUserById() {
        // given
        // when
        User result = userService.getById(1);

        // then
        assertThat(result.getNickname()).isEqualTo("kok202");
    }

    @Test
    @DisplayName("getById는 PENDING 상태인 유저는 찾아올 수 없다")
    void getPendingUserById() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("userCreate를 이용하여 유저를 생성할 수 있다")
    void createUserByUserCreate() {
        // given
        UserCreate userCreate = UserCreate.builder()
            .email("kok202@kakao.com")
            .address("Gyeongi")
            .nickname("kok202-k")
            .build();

        // when
        User result = userService.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    @DisplayName("userUpdate 를 이용하여 유저를 수정할 수 있다")
    void updateUserByUserUpdate() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
            .address("Incheon")
            .nickname("kok202-n")
            .build();

        // when
        userService.update(1, userUpdate);

        // then
        User user = userService.getById(1);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("Incheon");
        assertThat(user.getNickname()).isEqualTo("kok202-n");
    }

    @Test
    @DisplayName("user를 로그인 시키면 마지막 로그인 시간이 변경된다")
    void userLastLoginAt() {
        // given
        // when
        userService.login(1);

        // then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 인증 코드로 ACTIVE 시킬 수 있다")
    void userVerifyEmail() {
        // given
        // when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 잘못된 인증 코드를 받으면 에러를 던진다")
    void verifyEmailError() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
