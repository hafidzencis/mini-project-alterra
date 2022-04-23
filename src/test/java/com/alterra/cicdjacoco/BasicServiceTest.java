package com.alterra.cicdjacoco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alterra.cicdjacoco.service.BasicService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

/**
 * BasicServiceTest
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BasicService.class)
public class BasicServiceTest {

    @Autowired
    private BasicService basicService;

    @Test
    void basicSuccess_Test(){
        ResponseEntity<Object> responseEntity = basicService.basic();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void ErrorSuccess_Test(){
        ResponseEntity<Object> responseEntity = basicService.error("any");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }
    
}