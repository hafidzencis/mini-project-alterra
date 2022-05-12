package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
import com.alterra.cicdjacoco.repository.CoachRepository;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CoachService.class)
public class CoachServiceTest {

    @MockBean
    private CoachRepository coachRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Autowired
    private CoachService coachService;

    @Test
    public void addCoachSuccess_Test(){

        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        CoachDto coachDto = CoachDto.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();


        when(modelMapper.map(any(),eq(CoachDao.class))).thenReturn(coachDao);
        when(modelMapper.map(any(),eq(CoachDto.class))).thenReturn(coachDto);
        when(coachRepository.save(any())).thenReturn(coachDao);
        ResponseEntity<Object> responseEntity = coachService.createNewCoach(CoachDto.builder()
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CoachDto data = (CoachDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("luhut",data.getCoachName());
        assertEquals("085229903221",data.getTelephoneNumber());
        assertEquals("Padang",data.getAddress());


    }

    @Test
    public void addCoachException_Test(){
        when(coachRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = coachService.createNewCoach(CoachDto.builder()
                .id(1L)
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getAllCoach_Test(){
        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("budiman sujtmiko")
                .address("bekasi")
                .telephoneNumber("089090899721")
                .build();
        when(coachRepository.findAll()).thenReturn(List.of(coachDao));
        when(modelMapper.map(any(),eq(CoachDto.class)))
                .thenReturn(CoachDto.builder()
                        .id(1L)
                        .coachName("budiman sujtmiko")
                        .address("bekasi")
                        .telephoneNumber("089090899721")
                        .build());
        ResponseEntity<Object> responseEntity = coachService.getAllCoach();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<CoachDto> coachDtoList = (List<CoachDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,coachDtoList.size());
    }
//
//
    @Test
    public void getCoachById_Success_Test(){
        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("budiman sujatmiko")
                .address("bekasi")
                .telephoneNumber("089090899721")
                .build();

        CoachDto coachDto = CoachDto.builder()
                .id(1L)
                .coachName("budiman sujatmiko")
                .address("bekasi")
                .telephoneNumber("089090899721")
                .build();
        when(coachRepository.findById(1L)).thenReturn(Optional.of(coachDao));
        when(modelMapper.map(any(),eq(CoachDto.class))).thenReturn(coachDto);
        ResponseEntity<Object> responseEntity = coachService.getCoachById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CoachDto data = (CoachDto) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("budiman sujatmiko",data.getCoachName());


    }

    @Test
    void updateCoachSuccess_Test(){
        CoachDao coachDao = CoachDao.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();

        CoachDto coachDto = CoachDto.builder()
                .id(1L)
                .coachName("luhut")
                .telephoneNumber("085229903221")
                .address("Padang")
                .build();
        when(coachRepository.findById(anyLong())).thenReturn(Optional.of(coachDao));
        when(modelMapper.map(any(),eq(CoachDao.class))).thenReturn(coachDao);
        when(modelMapper.map(any(),eq(CoachDto.class))).thenReturn(coachDto);
        when(coachRepository.save(any())).thenReturn(coachDao);
        ResponseEntity<Object> responseEntity = coachService.updateCoach(1L,CoachDto.builder()
                .coachName("jokowi")
                .telephoneNumber("085229606033")
                .address("Padang")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CoachDto data = (CoachDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("luhut",data.getCoachName());
        assertEquals("085229903221",data.getTelephoneNumber());
        assertEquals("Padang",data.getAddress());

    }

    @Test
    public void deleteCoachSuccess_Test(){
        when(coachRepository.findById(anyLong())).thenReturn(Optional.of(CoachDao.builder()
                .id(1L)
                .coachName("jokowi")
                .telephoneNumber("085229606033")
                .address("Padang")
                .build()));
        doNothing().when(coachRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) coachService.deleteCoach(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(coachRepository,times(1)).delete(any());
    }

}
