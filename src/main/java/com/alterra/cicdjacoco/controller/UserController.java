package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewUser(@RequestBody UserDto request){
        return userService.createNewUser(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        return userService.getUserByid(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id,@RequestBody UserDto userDto){
        return userService.updateService(id,userDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }


}
