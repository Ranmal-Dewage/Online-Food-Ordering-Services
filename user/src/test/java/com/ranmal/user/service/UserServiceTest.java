package com.ranmal.user.service;

import com.ranmal.user.dto.UserLoginRequestDTO;
import com.ranmal.user.dto.UserResponseDTO;
import com.ranmal.user.exception.BadRequestException;
import com.ranmal.user.exception.InvalidCredentialsException;
import com.ranmal.user.model.User;
import com.ranmal.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    private UserService underTestUserService;

    @BeforeEach
    void setUp() {
        underTestUserService = new UserService(userRepositoryMock);
    }

    @Test
    void checkAddNewUser() {
        //given
        User testUser = User.builder().
                firstName("Ranmal").
                lastName("Dewage").
                userName("RanmalD").
                email("rand@gmail.com").
                address("Colombo, Sri Lanka").
                hashCredential("abc123").
                build();
        given(userRepositoryMock.existsUserByEmail(testUser.getEmail())).willReturn(false);
        given(userRepositoryMock.saveAndFlush(testUser)).willAnswer((invocation) -> {
            User user = invocation.getArgument(0, User.class);
            user.setUserId(1);
            return user;
        });

        //when
        UserResponseDTO result = underTestUserService.addNewUser(testUser);

        //then
        assertThat(result).isInstanceOf(UserResponseDTO.class);
    }

    @Test
    void checkAddNewUserAlreadyExists() {
        //given
        User testUser = User.builder().
                firstName("Ranmal").
                lastName("Dewage").
                userName("RanmalD").
                email("rand@gmail.com").
                address("Colombo, Sri Lanka").
                hashCredential("abc123").
                build();
        given(userRepositoryMock.existsUserByEmail(testUser.getEmail())).willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> underTestUserService.addNewUser(testUser)).
                isInstanceOf(BadRequestException.class).
                hasMessageContaining("Invalid Email Address");
    }

    @Test
    void checkAuthenticateUser() {
        //given
        UserLoginRequestDTO testLoginRequest = new UserLoginRequestDTO("rand@gmail.com", "abc123");
        User testUser = User.builder().
                userId(1).
                firstName("Ranmal").
                lastName("Dewage").
                userName("RanmalD").
                email("rand@gmail.com").
                address("Colombo, Sri Lanka").
                hashCredential("abc123").
                build();
        given(userRepositoryMock.findUserByEmail(testLoginRequest.getEmail())).willReturn(testUser);

        //when
        UserResponseDTO result = underTestUserService.authenticateUser(testLoginRequest);

        //then
        assertThat(result).isInstanceOf(UserResponseDTO.class);
    }

    @Test
    void checkAuthenticateUserInvalidCredentialsEmail(){
        //given
        UserLoginRequestDTO testLoginRequest = new UserLoginRequestDTO("rand@gmail.com", "abc123");
        given(userRepositoryMock.findUserByEmail(testLoginRequest.getEmail())).willReturn(null);

        //when
        //then
        assertThatThrownBy(() -> underTestUserService.authenticateUser(testLoginRequest)).
                isInstanceOf(InvalidCredentialsException.class).
                hasMessageContaining("Credentials are Not Valid");
    }

    @Test
    void checkAuthenticateUserInvalidCredentialsPassword(){
        //given
        UserLoginRequestDTO testLoginRequest = new UserLoginRequestDTO("rand@gmail.com", "qwe456");
        User testUser = User.builder().
                userId(1).
                firstName("Ranmal").
                lastName("Dewage").
                userName("RanmalD").
                email("rand@gmail.com").
                address("Colombo, Sri Lanka").
                hashCredential("abc123").
                build();
        given(userRepositoryMock.findUserByEmail(testLoginRequest.getEmail())).willReturn(testUser);

        //when
        //then
        assertThatThrownBy(() -> underTestUserService.authenticateUser(testLoginRequest)).
                isInstanceOf(InvalidCredentialsException.class).
                hasMessageContaining("Credentials are Not Valid");
    }

}