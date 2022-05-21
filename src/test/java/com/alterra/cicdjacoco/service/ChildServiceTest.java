package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
import com.alterra.cicdjacoco.repository.TeamRepository;
import com.alterra.cicdjacoco.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChildService.class)
public class ChildServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ChildService childService;


    @Test
    void addChooseTeamSuccess_Test(){
        UserDao userDao =UserDao.builder()
                .id(1L)
                .nameUser("hafidz")
                .alamat("bangunsari")
                .telephoneNumber("087356890991")
                .username("hafidzencis")
                .password("jokowilover")
                .author("USER")
                .build();

        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDao));
        when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        when(childRepository.save(any())).thenReturn(childDao);
        ResponseEntity<Object> responseEntity = childService.createNewChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder()
                        .id(1L)
                        .build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ChildDto data = (ChildDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("kaesang",data.getPlayerName());
        assertEquals("Surakarta",data.getPlaceDob());
        assertEquals(LocalDate.parse("1995-12-11"),data.getDob());
        assertEquals(childDto.getUser().getId(),data.getUser().getId());

    }

    @Test
    void addChooseTeamNotFoundId_Test(){
        UserDao userDao =UserDao.builder()
                .id(1L)
                .nameUser("hafidz")
                .alamat("bangunsari")
                .telephoneNumber("087356890991")
                .username("hafidzencis")
                .password("jokowilover")
                .author("USER")
                .build();

        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        when(childRepository.save(any())).thenReturn(childDao);
        ResponseEntity<Object> responseEntity = childService.createNewChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder()
                        .id(1L)
                        .build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addChildException_Test(){
        when(userRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = childService.createNewChild(ChildDto.builder()
                .user(UserDto.builder().id(1L).build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getAllChild_Test(){
        ChildDao child = ChildDao.builder()
                .id(1L)
                .playerName("Puan Maharani")
                .placeDob("Sragen")
                .dob(LocalDate.parse("1965-09-10"))
                .build();
        when(childRepository.findAll()).thenReturn(List.of(child));
        when(modelMapper.map(any(),eq(ChildDto.class)))
                .thenReturn(ChildDto.builder()
                        .id(1L)
                        .playerName("Puan Maharani")
                        .placeDob("Sragen")
                        .dob(LocalDate.parse("1965-09-10"))
                        .build());
        ResponseEntity<Object> responseEntity = childService.getAllChild();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ChildDto> childDtoList = (List<ChildDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,childDtoList.size());
    }

    @Test
    void getAllChildNotFound_Test(){
        ChildDao child = ChildDao.builder()
                .id(1L)
                .playerName("Puan Maharani")
                .placeDob("Sragen")
                .dob(LocalDate.parse("1965-09-10"))
                .user(UserDao.builder()
                        .id(1L)
                        .build())
                .build();
//        UserDto userDto = UserDto.builder()
//                .id(1L)
//                .nameUser("Megawati")
//                .telephoneNumber("089890789123")
//                .alamat("Bekasi")
//                .username("hafidzencis")
//                .password("jokowilover")
//                .isAdmin("true")
//                .build();

//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(childRepository.findAll()).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(ChildDto.class)))
                .thenReturn(ChildDto.builder()
                        .id(1L)
                        .playerName("Puan Maharani")
                        .placeDob("Sragen")
                        .dob(LocalDate.parse("1965-09-10"))
                        .user(UserDto.builder().id(1L).build())
                        .build());
        ResponseEntity<Object> responseEntity = childService.getAllChild();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

//        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1,childDtoList.size());
    }


    @Test
    void getAllChildEXception_Test(){
        ChildDao child = ChildDao.builder()
                .id(1L)
                .playerName("Puan Maharani")
                .placeDob("Sragen")
                .dob(LocalDate.parse("1965-09-10"))
                .user(UserDao.builder()
                        .id(1L)
                        .build())
                .build();
//        UserDto userDto = UserDto.builder()
//                .id(1L)
//                .nameUser("Megawati")
//                .telephoneNumber("089890789123")
//                .alamat("Bekasi")
//                .username("hafidzencis")
//                .password("jokowilover")
//                .isAdmin("true")
//                .build();

//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(childRepository.findAll()).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(ChildDto.class)))
                .thenReturn(ChildDto.builder()
                        .id(1L)
                        .playerName("Puan Maharani")
                        .placeDob("Sragen")
                        .dob(LocalDate.parse("1965-09-10"))
                        .user(UserDto.builder().id(1L).build())
                        .build());
        ResponseEntity<Object> responseEntity = childService.getAllChild();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

//        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1,childDtoList.size());
    }

    @Test
    void getChildById_Success_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("prabowo")
                .placeDob("Jakarta")
                .dob(LocalDate.parse("1960-12-12"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("prabowo")
                .placeDob("Jakarta")
                .dob(LocalDate.parse("1960-12-12"))
                .build();
        when(childRepository.findById(1L)).thenReturn(Optional.of(childDao));
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        ResponseEntity<Object> responseEntity = childService.getChildById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ChildDto data = (ChildDto) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("prabowo",data.getPlayerName());


    }

    @Test
    public void getChildById_NotFoundId_Test(){
        when(childRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = childService.getChildById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getChildhById_Exception_Test(){
        when(childRepository.findById(1L)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = childService.getChildById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateChildSuccess_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(childRepository.findById(anyLong())).thenReturn(Optional.of(childDao));
        when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        when(childRepository.save(any())).thenReturn(childDao);
        ResponseEntity<Object> responseEntity = childService.updateChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder()
                        .id(1L).build())
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ChildDto data = (ChildDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("jokowi",data.getPlayerName());
        assertEquals("Surakarta",data.getPlaceDob());
        assertEquals(LocalDate.parse("1995-12-11"),data.getDob());
        assertEquals(childDto.getUser().getId(),data.getId());
    }

    @Test
    void updateChildIdNotFOund_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(childRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
//        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
//        when(childRepository.save(any())).thenReturn(childDao);
        ResponseEntity<Object> responseEntity = childService.updateChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder()
                        .id(1L).build())
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void updateChildIdExceptiom_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(childRepository.findById(anyLong())).thenReturn(Optional.of(childDao));
        when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        when(childRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = childService.updateChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder()
                        .id(1L).build())
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void deleteChildSuccess_Test(){
        when(childRepository.findById(anyLong())).thenReturn(Optional.of(ChildDao.builder()
                .id(1L)
                .playerName("New person")
                .placeDob("New place")
                .dob(LocalDate.parse("1997-02-10"))
                .build()));
        doNothing().when(childRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) childService.deleteChild(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(childRepository,times(1)).delete(any());
    }

    @Test
    void deleteChildNotFoundId_Test(){
        when(childRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(childRepository).delete(any());

        ResponseEntity<Object> responseEntity = childService.deleteChild(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void deleteChildException_Test(){
        when(childRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(childRepository).delete(any());

        ResponseEntity<Object> responseEntity = childService.deleteChild(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }



    @Test
    void getChildByName_Success_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDao.builder().id(1L).build())
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();

        when(childRepository.findChildByName("jokowi")).thenReturn(List.of(childDao));
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        ResponseEntity<Object> responseEntity = childService.findChildByName("jokowi");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ChildDto> childDtoList = (List<ChildDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,childDtoList.size());


    }

    @Test
    void getTeamByName_NotFound_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDao.builder().id(1L).build())
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();
        when(childRepository.findChildByName("jokowi")).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        ResponseEntity<Object> responseEntity = childService.findChildByName("jokowi");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getTeamByName_Exception_Test(){
        ChildDao childDao = ChildDao.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDao.builder().id(1L).build())
                .build();
        ChildDto childDto = ChildDto.builder()
                .id(1L)
                .playerName("jokowi")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .user(UserDto.builder().id(1L).build())
                .build();
        when(childRepository.findChildByName("jokowi")).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        ResponseEntity<Object> responseEntity = childService.findChildByName("jokowi");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


}