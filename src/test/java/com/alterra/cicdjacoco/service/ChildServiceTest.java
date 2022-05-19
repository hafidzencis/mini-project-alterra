package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.common.ApiResponse;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
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
    private ChildRepository childRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ChildService childService;

    @Test
    void addChildSuccess_Test(){
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
                .build();

        when(modelMapper.map(any(),eq(ChildDao.class))).thenReturn(childDao);
        when(modelMapper.map(any(),eq(ChildDto.class))).thenReturn(childDto);
        when(childRepository.save(any())).thenReturn(childDao);
        ResponseEntity<Object> responseEntity = childService.createNewChild(ChildDto.builder()
                .playerName("kaesang")
                .placeDob("Surakarta")
                .dob(LocalDate.parse("1995-12-11"))
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ChildDto data = (ChildDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("kaesang",data.getPlayerName());
        assertEquals("Surakarta",data.getPlaceDob());
        assertEquals(LocalDate.parse("1995-12-11"),data.getDob());

    }

    @Test
    void getAllChild_Test(){
        ChildDao child = ChildDao.builder()
                .id(1L)
                .playerName("Puan Maharani")
                .placeDob("Sragne")
                .dob(LocalDate.parse("1965-09-10"))
                .build();
        when(childRepository.findAll()).thenReturn(List.of(child));
        when(modelMapper.map(any(),eq(ChildDto.class)))
                .thenReturn(ChildDto.builder()
                        .id(1L)
                        .playerName("Puan Maharani")
                        .placeDob("Sragne")
                        .dob(LocalDate.parse("1965-09-10"))
                        .build());
        ResponseEntity<Object> responseEntity = childService.getAllChild();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ChildDto> childDtoList = (List<ChildDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMassage.KEY_FOUND,Objects.requireNonNull(apiResponse).getData());
        assertEquals(1,childDtoList.size());
    }

//    @Test
//    void updateChildSuccess_Test(){
//
//    }

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

}