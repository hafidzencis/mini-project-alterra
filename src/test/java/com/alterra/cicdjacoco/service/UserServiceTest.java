package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsernameSuccess_Test() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();

        when(userRepository.getDistinctTopByUsername(any())).thenReturn(userDao);

        UserDetails userDetails = userService.loadUserByUsername("hafidzencis");
        assertEquals("hafidzencis", userDetails.getUsername());
        assertEquals("jokowilover", userDetails.getPassword());
    }

    @Test
    public void getAllUsersNotFound_Test(){
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();
        when(userRepository.findAll()).thenReturn(List.of(userDao));
//        when(modelMapper.map(any(),eq(UserDto.class)))
//                .thenReturn(UserDto.builder()
//                        .id(1L)
//                        .nameUser("Hafidz Febrian")
//                        .alamat("Bangunsari")
//                        .telephoneNumber("087689099766")
//                        .author("USER")
//                        .username("hafidzencis")
//                        .password("jokowilover")
//                        .build());
        ResponseEntity<Object> responseEntity = userService.getAllUser();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<UserDto> userDtoList = (List<UserDto>) apiResponse.getData();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

//        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1,userDtoList.size());
    }

    @Test
    public void getAllCoach_Exception_Test(){
        when(userRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = userService.getAllUser();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void getUserById_Success_Test(){
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(),eq(UserDto.class))).thenReturn(userDto);
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UserDto data = (UserDto) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("Hafidz Febrian",data.getNameUser());
        assertEquals("Bangunsari",data.getAlamat());
        assertEquals("087689099766",data.getTelephoneNumber());
    }

    @Test
    public void getUserById_NotFoundId_Test(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getUserById_Exception_Test(){
        when(userRepository.findById(1L)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateUserSuccess_Test(){
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(),eq(UserDao.class))).thenReturn(userDao);
        when(modelMapper.map(any(),eq(UserDto.class))).thenReturn(userDto);
        when(userRepository.save(any())).thenReturn(userDao);
        ResponseEntity<Object> responseEntity = userService.updateService(1L,UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UserDto data = (UserDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Hafidz Febrian",data.getNameUser());
        assertEquals("Bangunsari",data.getAlamat());
        assertEquals("087689099766",data.getTelephoneNumber());

    }

    @Test
    void updateCoachNotFound_Test(){
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(modelMapper.map(any(),eq(CoachDao.class))).thenReturn(coachDao);
//        when(modelMapper.map(any(),eq(CoachDto.class))).thenReturn(coachDto);
//        when(coachRepository.save(any())).thenReturn(coachDao);
        ResponseEntity<Object> responseEntity = userService.updateService(1L,UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }
    @Test
    void updateCoachException_Test(){
        UserDao userDao = UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build();
        when(userRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        when(modelMapper.map(any(),eq(CoachDao.class))).thenReturn(coachDao);
//        when(modelMapper.map(any(),eq(CoachDto.class))).thenReturn(coachDto);
//        when(coachRepository.save(any())).thenReturn(coachDao);
        ResponseEntity<Object> responseEntity = userService.updateService(1L,UserDto.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteUserSuccess_Test(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserDao.builder()
                .id(1L)
                .nameUser("Hafidz Febrian")
                .alamat("Bangunsari")
                .telephoneNumber("087689099766")
                .author("USER")
                .username("hafidzencis")
                .password("jokowilover")
                .build()));
        doNothing().when(userRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) userService.deleteUser(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(userRepository,times(1)).delete(any());
    }

    @Test
    public void deleteCoachNotFound_Test(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(userRepository).delete(any());

        ResponseEntity<Object> responseEntity = userService.deleteUser(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteCoachException_Test(){
        when(userRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(userRepository).delete(any());

        ResponseEntity<Object> responseEntity = userService.deleteUser(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


}
