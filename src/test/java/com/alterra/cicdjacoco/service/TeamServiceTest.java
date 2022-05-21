package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
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
@SpringBootTest(classes = TeamService.class)
public class TeamServiceTest {

    @MockBean
    private CoachRepository coachRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private TeamService teamService;

    @Test
    public void addTeamSuccess_Test(){

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

        when(coachRepository.findById(anyLong())).thenReturn(Optional.of(coachDao));
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(),eq(TeamDao.class))).thenReturn(teamDao);
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        when(teamRepository.save(any())).thenReturn(teamDao);
        ResponseEntity<Object> responseEntity = teamService.createNewTeam(TeamDto.builder()
                        .teamName("Jokowi Lover")
                        .coach(CoachDto.builder()
                                .id(1L)
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(1L)
                                .build())
                        .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TeamDto data = (TeamDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Jokowi Lover",data.getTeamName());
        assertEquals(teamDto.getCoach().getId(),data.getCoach().getId());
        assertEquals(teamDto.getSchedule().getId(),data.getSchedule().getId());
    }

    @Test
    void addScheduleNotFound_Test(){
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = teamService.createNewTeam(TeamDto.builder()
                        .id(1L)
                        .schedule(ScheduleDto.builder().id(1L).build())
                        .coach(CoachDto.builder().id(1L).build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void addCoachNotFound_Test(){
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        when(coachRepository.findById(1L)).thenReturn(Optional.of(CoachDao.builder().id(1L).build()));
        ResponseEntity<Object> responseEntity = teamService.createNewTeam(TeamDto.builder()
                .id(1L)
                .coach(CoachDto.builder().id(1L).build())
                .schedule(ScheduleDto.builder().id(1L).build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void addScheduleException_Test(){
        when(scheduleRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = teamService.createNewTeam(TeamDto.builder()
                        .schedule(ScheduleDto.builder().id(1L).build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getAllTeamSuccess_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        when(teamRepository.findAll()).thenReturn(List.of(teamDao));
        when(modelMapper.map(any(),eq(TeamDto.class)))
                .thenReturn(TeamDto.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .build());
        ResponseEntity<Object> responseEntity = teamService.getAllTeam();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TeamDto> teamDtoList = (List<TeamDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,teamDtoList.size());
    }

    @Test
    public void getAllTeamException_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        when(teamRepository.findAll()).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(TeamDto.class)))
                .thenReturn(TeamDto.builder()
                        .id(1L)
                        .teamName("Jokowi Lover")
                        .build());
        ResponseEntity<Object> responseEntity = teamService.getAllTeam();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TeamDto> teamDtoList = (List<TeamDto>) apiResponse.getData();


//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


    @Test
    void getTeamById_Success_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(teamDao));
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        ResponseEntity<Object> responseEntity = teamService.getTeambyId(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TeamDto data = (TeamDto) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("Jokowi Lover",data.getTeamName());


    }

    @Test
    void getTeamById_NotFound_Test(){
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = teamService.getTeambyId(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());


    }

    @Test
    void getTeamById_Excpetion_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        when(teamRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        ResponseEntity<Object> responseEntity = teamService.getTeambyId(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void updateTeamSuccess_Test(){
        TeamDao teamDao =TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();

        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(teamDao));
        when(modelMapper.map(any(),eq(TeamDao.class))).thenReturn(teamDao);
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        when(teamRepository.save(any())).thenReturn(teamDao);
        ResponseEntity<Object> responseEntity = teamService.updateTeam(1L,TeamDto.builder()
                .teamName("Anti jokowi")
                .build() );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TeamDto teamDto1 = (TeamDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,teamDto1.getId());
        assertEquals("Jokowi Lover",teamDto1.getTeamName());

    }

    @Test
    void updateTeam_NotFound_Test(){

        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = teamService.updateTeam(1L,TeamDto.builder()
                .teamName("Anti jokowi")
                .build() );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateTeam_Exception_Test(){
        when(teamRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = teamService.updateTeam(1L,TeamDto.builder()
                .teamName("Anti jokowi")
                .build() );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void deleteTeamSuccess_Test(){
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build()));
        doNothing().when(teamRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) teamService.deleteTeam(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(teamRepository,times(1)).delete(any());
    }

    @Test
    void DeleteTeam_NotFoundId_Test(){

        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(teamRepository).delete(any());

        ResponseEntity<Object> responseEntity = teamService.deleteTeam(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }
    @Test
    public void deleteTeamException_Test(){
        when(teamRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(teamRepository).delete(any());

        ResponseEntity<Object> responseEntity = teamService.deleteTeam(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getTeamByName_Success_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();

        when(teamRepository.findTeamByName("Jokowi Lover")).thenReturn(List.of(teamDao));
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        ResponseEntity<Object> responseEntity = teamService.findTeamByName("Jokowi Lover");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TeamDto> teamDtoList = (List<TeamDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,teamDtoList.size());


    }

    @Test
    void getTeamByName_NotFound_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        when(teamRepository.findTeamByName("Jokowi Lover")).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        ResponseEntity<Object> responseEntity = teamService.findTeamByName("Jokowi Lover");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getTeamByName_Exception_Test(){
        TeamDao teamDao = TeamDao.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        TeamDto teamDto = TeamDto.builder()
                .id(1L)
                .teamName("Jokowi Lover")
                .build();
        //when(teamRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        when(teamRepository.findTeamByName("Jokowi Lover")).thenThrow(NullPointerException.class);
        when(modelMapper.map(any(),eq(TeamDto.class))).thenReturn(teamDto);
        ResponseEntity<Object> responseEntity = teamService.findTeamByName("Jokowi Lover");

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }
}
