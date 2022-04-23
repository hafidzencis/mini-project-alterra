package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/basic")
public class BasicController {

    @Autowired
    private BasicService basicService;

    @GetMapping(value = "")
    public ResponseEntity<Object> basic(){
        return basicService.basic();
    }

}
