package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.*;
import com.alterra.cicdjacoco.domain.dto.*;
import com.alterra.cicdjacoco.repository.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChooseTeamService.class)
public class ChooseTeamServiceTest {

//    @MockBean
//    private CoachRepository coachRepository;

//    @MockBean
//    private ScheduleRepository scheduleRepository;

//    @MockBean
//    private UserRepository userRepository;

    @MockBean
    private ChooseTeamRepository chooseTeamRepository;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private TeamRepository teamRepository;

    @Autowired
    private ChooseTeamService chooseTeamService;


    @Test
    public void addChooseTeamSuccess_Test(){


        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();

//        CoachDto coachDto = CoachDto.builder()
//                .id(1L)
//                .coachName("luhut")
//                .telephoneNumber("085229903221")
//                .address("Padang")
//                .build();

        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(coachDao)
                .schedule(scheduleDao)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(CoachDto.builder()
                        .id(1L)
                        .build())
                .schedule(ScheduleDto.builder()
                        .id(1L)
                        .build())
                .build();

        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                        .id(1L)
                        .childName("Jokowi")
                        .childs(childDao)
                        .teams(teamDao)
                        .build();

        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(ChildDto.builder()
                        .id(1L)
                        .playerName("Jokowi")
                        .placeDob("Surakarta")
                        .dob(LocalDate.parse("2015-10-10"))
                        .user(UserDto.builder()
                                .id(1L)
                                .nameUser("Hafidz")
                                .alamat("Sragen")
                                .telephoneNumber("089099789675")
                                .author("USER")
                                .username("hafidzencis")
                                .password("gedanglovers")
                                .build())
                        .build())
                .teams(teamDto)
                .build();

        when(childRepository.findById(1L)).thenReturn(Optional.of(childDao));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(teamDao));
        when(modelMapper.map(any(),eq(ChooseTeamDao.class))).thenReturn(chooseTeamDao);
        when(modelMapper.map(any(),eq(ChooseTeamDto.class))).thenReturn(chooseTeamDto);
        when(chooseTeamRepository.save(any())).thenReturn(chooseTeamDao);
        ResponseEntity<Object> responseEntity = chooseTeamService.createNewChooseTeam(1L,1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ChooseTeamDto data = (ChooseTeamDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Jokowi",data.getChildName());

    }

    @Test
    public void addChooseTeamException_Test(){

        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();

//        CoachDto coachDto = CoachDto.builder()
//                .id(1L)
//                .coachName("luhut")
//                .telephoneNumber("085229903221")
//                .address("Padang")
//                .build();

        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(coachDao)
                .schedule(scheduleDao)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(CoachDto.builder()
                        .id(1L)
                        .build())
                .schedule(ScheduleDto.builder()
                        .id(1L)
                        .build())
                .build();

        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(childDao)
                .teams(teamDao)
                .build();

        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(ChildDto.builder()
                        .id(1L)
                        .playerName("Jokowi")
                        .placeDob("Surakarta")
                        .dob(LocalDate.parse("2015-10-10"))
                        .user(UserDto.builder()
                                .id(1L)
                                .nameUser("Hafidz")
                                .alamat("Sragen")
                                .telephoneNumber("089099789675")
                                .author("USER")
                                .username("hafidzencis")
                                .password("gedanglovers")
                                .build())
                        .build())
                .teams(teamDto)
                .build();

        when(childRepository.findById(1L)).thenReturn(Optional.of(childDao));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(teamDao));
        when(modelMapper.map(any(),eq(ChooseTeamDao.class))).thenReturn(chooseTeamDao);
        when(modelMapper.map(any(),eq(ChooseTeamDto.class))).thenReturn(chooseTeamDto);
        when(chooseTeamRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = chooseTeamService.createNewChooseTeam(1L,1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }
    @Test
    public void addChildException_Test(){
        when(childRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = chooseTeamService.createNewChooseTeam(1L,1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void addTeamException_Test(){
        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();
        when(childRepository.findById(1L)).thenReturn(Optional.of(childDao));
        when(teamRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = chooseTeamService.createNewChooseTeam(1L,1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getAllChooseTeam_Test(){

        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();


        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(coachDao)
                .schedule(scheduleDao)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(CoachDto.builder()
                        .id(1L)
                        .build())
                .schedule(ScheduleDto.builder()
                        .id(1L)
                        .build())
                .build();

        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(childDao)
                .teams(teamDao)
                .build();

        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(ChildDto.builder()
                        .id(1L)
                        .playerName("Jokowi")
                        .placeDob("Surakarta")
                        .dob(LocalDate.parse("2015-10-10"))
                        .user(UserDto.builder()
                                .id(1L)
                                .nameUser("Hafidz")
                                .alamat("Sragen")
                                .telephoneNumber("089099789675")
                                .author("USER")
                                .username("hafidzencis")
                                .password("gedanglovers")
                                .build())
                        .build())
                .teams(teamDto)
                .build();

        when(chooseTeamRepository.findAll()).thenReturn(List.of(chooseTeamDao));
        when(modelMapper.map(any(),eq(ChooseTeamDto.class)))
                .thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.getAllChooseTeam();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ChooseTeamDto> chooseTeamDtoList = (List<ChooseTeamDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, chooseTeamDtoList.size());
    }

    @Test
    public void getAllChooseTeamException_Test(){

        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();

        CoachDto coachDto = CoachDto.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(coachDao)
                .schedule(scheduleDao)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(CoachDto.builder()
                        .id(1L)
                        .build())
                .schedule(ScheduleDto.builder()
                        .id(1L)
                        .build())
                .build();

        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(childDao)
                .teams(teamDao)
                .build();

        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(ChildDto.builder()
                        .id(1L)
                        .playerName("Jokowi")
                        .placeDob("Surakarta")
                        .dob(LocalDate.parse("2015-10-10"))
                        .user(UserDto.builder()
                                .id(1L)
                                .nameUser("Hafidz")
                                .alamat("Sragen")
                                .telephoneNumber("089099789675")
                                .author("USER")
                                .username("hafidzencis")
                                .password("gedanglovers")
                                .build())
                        .build())
                .teams(teamDto)
                .build();

        when(chooseTeamRepository.findAll()).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(ChooseTeamDto.class)))
                .thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.getAllChooseTeam();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void getAllChooseTeamNotFound_Test(){

        UserDao userDao = UserDao.builder()
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
                .user(userDao)
                .build();

        CoachDto coachDto = CoachDto.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(coachDao)
                .schedule(scheduleDao)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .coach(CoachDto.builder()
                        .id(1L)
                        .build())
                .schedule(ScheduleDto.builder()
                        .id(1L)
                        .build())
                .build();

        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(childDao)
                .teams(teamDao)
                .build();

        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("Jokowi")
                .childs(ChildDto.builder()
                        .id(1L)
                        .playerName("Jokowi")
                        .placeDob("Surakarta")
                        .dob(LocalDate.parse("2015-10-10"))
                        .user(UserDto.builder()
                                .id(1L)
                                .nameUser("Hafidz")
                                .alamat("Sragen")
                                .telephoneNumber("089099789675")
                                .author("USER")
                                .username("hafidzencis")
                                .password("gedanglovers")
                                .build())
                        .build())
                .teams(teamDto)
                .build();

        when(chooseTeamRepository.findAll()).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(ChooseTeamDto.class)))
                .thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.getAllChooseTeam();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }
    //
    @Test
    void getChooseTeamByChildName_Success_Test(){
       ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
               .id(1L)
               .childName("kaesang")
               .teams(TeamDao.builder()
                       .id(1L)
                       .teamName("Jokowi Lover")
                       .coach(CoachDao.builder()
                               .id(1L)
                               .build())
                       .schedule(ScheduleDao.builder()
                               .id(1L)
                               .build())
                       .build())
               .childs(ChildDao.builder()
                       .id(1L)
                       .user(UserDao.builder()
                               .id(1L)
                               .build())
                       .build())
               .build();


        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("kaesang")
                .teams(TeamDto.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .coach(CoachDto.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .childs(ChildDto.builder()
                        .id(1L)
                        .user(UserDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();

        when(chooseTeamRepository.findChildByName("kaesang")).thenReturn(List.of(chooseTeamDao));
        when(modelMapper.map(any(),eq(ChooseTeamDto.class))).thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.findChildByName("kaesang");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ChildDto> childDtoList = (List<ChildDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,childDtoList.size());


    }

    @Test
    void getChooseTeamByChildName_NotFound_Test(){
        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("kaesang")
                .teams(TeamDao.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .coach(CoachDao.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDao.builder()
                                .id(1L)
                                .build())
                        .build())
                .childs(ChildDao.builder()
                        .id(1L)
                        .user(UserDao.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();


        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("kaesang")
                .teams(TeamDto.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .coach(CoachDto.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .childs(ChildDto.builder()
                        .id(1L)
                        .user(UserDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();

        when(chooseTeamRepository.findChildByName("kaesang")).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(ChooseTeamDto.class))).thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.findChildByName("kaesang");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getChooseTeamByChildName_Exception_Test(){
        ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                .id(1L)
                .childName("kaesang")
                .teams(TeamDao.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .coach(CoachDao.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDao.builder()
                                .id(1L)
                                .build())
                        .build())
                .childs(ChildDao.builder()
                        .id(1L)
                        .user(UserDao.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();


        ChooseTeamDto chooseTeamDto = ChooseTeamDto.builder()
                .id(1L)
                .childName("kaesang")
                .teams(TeamDto.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .coach(CoachDto.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .childs(ChildDto.builder()
                        .id(1L)
                        .user(UserDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();

        when(chooseTeamRepository.findChildByName("kaesang")).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(ChooseTeamDto.class))).thenReturn(chooseTeamDto);
        ResponseEntity<Object> responseEntity = chooseTeamService.findChildByName("kaesang");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteChooseTeamSuccess_Test(){
        when(chooseTeamRepository.findById(anyLong())).thenReturn(Optional.of(ChooseTeamDao.builder()
                .id(1L)
                .childName("Kaesang")
                .teams(TeamDao.builder()
                        .id(1L)
                        .build())
                .childs(ChildDao.builder()
                        .id(1L)
                        .build())
                .build()));
        doNothing().when(chooseTeamRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) chooseTeamService.deleteChooseTeam(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(chooseTeamRepository,times(1)).delete(any());
    }

    @Test
    public void deleteChooseTeamNotFoundId_Test(){
        when(chooseTeamRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(chooseTeamRepository).delete(any());

        ResponseEntity<Object> responseEntity = chooseTeamService.deleteChooseTeam(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteChooseTeamException_Test(){
        when(chooseTeamRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(chooseTeamRepository).delete(any());

        ResponseEntity<Object> responseEntity = chooseTeamService.deleteChooseTeam(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


}
