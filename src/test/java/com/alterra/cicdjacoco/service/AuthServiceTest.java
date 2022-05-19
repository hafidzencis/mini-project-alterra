package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.config.JwtTokenProvider;
import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.domain.dto.TokenResponse;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
public class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Test
    void registerUsersSuccess_Test() {

        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .build();


        when(userRepository.existsByUsername(userDao.getUsername())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userDao);

        ResponseEntity<Object> responseEntity = authService.register(UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UserDto users = (UserDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void registerUsers_Admin_Success_Test() {

        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .author("ADMIN")
                .build();


        when(userRepository.existsByUsername(userDao.getUsername())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userDao);

        ResponseEntity<Object> responseEntity = authService.register(UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .author("ADMIN")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UserDto users = (UserDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void registerUsersFail_UserExists_Test() {

        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .build();


        when(userRepository.existsByUsername(userDao.getUsername())).thenReturn(true);
        when(userRepository.save(any())).thenReturn(userDao);

        ResponseEntity<Object> responseEntity = authService.register(UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .username("hafidzencis")
                .password("jokowilover")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.USER_EXISTS,Objects.requireNonNull(apiResponse).getMessage());
    }
//
//    @Test
//    void registerUsersError_Test() {
//        when(usersRepository.getDistinctTopByUsername("nathan")).thenReturn(Users
//                .builder()
//                .id(1L)
//                .username("nathan")
//                .password("123456")
//                .name("Nathan Ramli")
//                .build());
//
//        try {
//            Users user = authService.register(
//                    UsersRequest
//                            .builder()
//                            .username("nathan")
//                            .password("123456")
//                            .name("Nathan Ramli")
//                            .build()
//            );
//            fail();
//        } catch (Exception e) {
//        }
//    }
//
    @Test
    void authenticatedAndGenerateTokenSuccess_Test() {
        when(jwtTokenProvider.generationToken(any())).thenReturn("TOKEN");

        ResponseEntity<?> responseEntity = authService.authenticateAndGenerateToken(UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build());
        ApiResponse response = (ApiResponse) responseEntity.getBody();
        TokenResponse tokenResponse = (TokenResponse) Objects.requireNonNull(response).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("TOKEN", tokenResponse.getToken());

    }

    @Test
    void authenticatedAndGenerateTokenFail_Test() {
        when(jwtTokenProvider.generationToken(any())).thenThrow(NullPointerException.class);

        ResponseEntity<?> responseEntity = authService.authenticateAndGenerateToken(UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build());
        ApiResponse apiresponse = (ApiResponse) responseEntity.getBody();
        //TokenResponse tokenResponse = (TokenResponse) Objects.requireNonNull(response).getData();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiresponse).getMessage());

    }
}
//}
