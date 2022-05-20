package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScheduleService.class)
public class ScheduleServiceTest {

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

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void addScheduleSuccess_Test(){

        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        when(modelMapper.map(any(),eq(ScheduleDao.class))).thenReturn(scheduleDao);
        when(modelMapper.map(any(),eq(ScheduleDto.class))).thenReturn(scheduleDto);
        when(scheduleRepository.save(any())).thenReturn(scheduleDao);
        ResponseEntity<Object> responseEntity = scheduleService.createNewSchedule(ScheduleDto.builder()
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ScheduleDto data = (ScheduleDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Tuesday,Thursday,Sunday",data.getDaySchedule());
        assertEquals("16:00",data.getTimeSchedule());


    }

    @Test
    public void addScheduleException_Test(){
        when(scheduleRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = scheduleService.createNewSchedule(ScheduleDto.builder()
                .id(1L)
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getAllSchedule_Test(){
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();
        when(scheduleRepository.findAll()).thenReturn(List.of(scheduleDao));
        when(modelMapper.map(any(),eq(ScheduleDto.class)))
                .thenReturn(ScheduleDto.builder()
                        .id(1L)
                        .daySchedule("Tuesday,Thursday,Sunday")
                        .timeSchedule("16:00")
                        .build());
        ResponseEntity<Object> responseEntity = scheduleService.getAllSchedule();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ScheduleDto> scheduleDtoList = (List<ScheduleDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,scheduleDtoList.size());
    }

    @Test
    public void getAllScheduel_Exception_Test(){
        when(scheduleRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = scheduleService.getAllSchedule();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


    @Test
    public void getScheduleById_Success_Test(){
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(),eq(ScheduleDto.class))).thenReturn(scheduleDto);
        ResponseEntity<Object> responseEntity = scheduleService.getScheduleById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ScheduleDto data = (ScheduleDto) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("Tuesday,Thursday,Sunday",data.getDaySchedule());


    }

    @Test
    public void getScheduleById_NotFoundId_Test(){
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = scheduleService.getScheduleById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getScheduleById_Exception_Test(){
        when(scheduleRepository.findById(1L)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = scheduleService.getScheduleById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateScheduleSuccess_Test(){
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(),eq(ScheduleDao.class))).thenReturn(scheduleDao);
        when(modelMapper.map(any(),eq(ScheduleDto.class))).thenReturn(scheduleDto);
        when(scheduleRepository.save(any())).thenReturn(scheduleDao);
        ResponseEntity<Object> responseEntity = scheduleService.updateSchedule(ScheduleDto.builder()
                .daySchedule("Tuesday,Monday,Thursday")
                .timeSchedule("16:00")
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ScheduleDto data = (ScheduleDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Tuesday,Thursday,Sunday",data.getDaySchedule());
        assertEquals("16:00",data.getTimeSchedule());


    }


    @Test
    void updateScheduleIdNotFound_Test(){
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(modelMapper.map(any(),eq(ScheduleDao.class))).thenReturn(scheduleDao);
//        when(modelMapper.map(any(),eq(ScheduleDto.class))).thenReturn(scheduleDto);
//        when(scheduleRepository.save(any())).thenReturn(scheduleDao);
        ResponseEntity<Object> responseEntity = scheduleService.updateSchedule(ScheduleDto.builder()
                .daySchedule("Tuesday,Monday,Thursday")
                .timeSchedule("16:00")
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


    @Test
    void updateScheduleException_Test(){
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(),eq(ScheduleDao.class))).thenReturn(scheduleDao);
        when(modelMapper.map(any(),eq(ScheduleDto.class))).thenReturn(scheduleDto);
        when(scheduleRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = scheduleService.updateSchedule(ScheduleDto.builder()
                .daySchedule("Tuesday,Monday,Thursday")
                .timeSchedule("16:00")
                .build(),1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteScheduleSuccess_Test(){
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(ScheduleDao.builder()
                .id(1L)
                .daySchedule("Tuesday,Thursday,Sunday")
                .timeSchedule("16:00")
                .build()));
        doNothing().when(scheduleRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) scheduleService.deleteSchedule(1L).getBody();
        assertEquals(ResponseMassage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(scheduleRepository,times(1)).delete(any());
    }

    @Test
    public void deleteScheduleIdNotFound_Test(){
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(scheduleRepository).delete(any());

        ResponseEntity<Object> responseEntity = scheduleService.deleteSchedule(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }


    @Test
    public void deleteScheduleException_Test(){
        when(scheduleRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(scheduleRepository).delete(any());

        ResponseEntity<Object> responseEntity = scheduleService.deleteSchedule(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMassage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

}
