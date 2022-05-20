package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.service.CoachService;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/coach")
public class CoachController {

    @Autowired
    private UserService userService;

    @Autowired
    private CoachService coachService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewCoach(Principal principal, @RequestBody  CoachDto request){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return coachService.createNewCoach(request);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCoach(Principal principal){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return coachService.getAllCoach();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCoachById(Principal principal,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return coachService.getCoachById(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCoach(Principal principal,@PathVariable Long id,@RequestBody CoachDto request){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return coachService.updateCoach(id,request);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCoach(Principal principal, @PathVariable Long id){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return coachService.deleteCoach(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);


    }
}
