package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasicService {

    public ResponseEntity<Object> basic(){
        return ResponseUtil.build("Hello World From Spring Boot",null, HttpStatus.OK);
    }


}
