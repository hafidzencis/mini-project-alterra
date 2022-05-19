package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser(Principal principal){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return userService.getAllUser();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(Principal principal,@PathVariable Long id){
        return userService.getUserByid(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(Principal principal,@PathVariable Long id,@RequestBody UserDto userDto){
        return userService.updateService(id,userDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(Principal principal,@PathVariable Long id){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return userService.deleteUser(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }


}
