package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
import com.alterra.cicdjacoco.service.ScheduleService;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/schedule")
public class ScheduleController {
    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewSchedule(Principal principal, @RequestBody ScheduleDto request){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return scheduleService.createNewSchedule(request);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllSchedule(Principal principal){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return scheduleService.getAllSchedule();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getScheduleById(Principal principal,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return scheduleService.getScheduleById(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateSchedule(Principal principal,@RequestBody ScheduleDto request,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return scheduleService.updateSchedule(request,id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteSchedule(Principal principal,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return scheduleService.deleteSchedule(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }
}
