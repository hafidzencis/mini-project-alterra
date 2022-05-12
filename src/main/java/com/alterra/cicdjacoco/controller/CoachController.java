package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.service.CoachService;
import com.alterra.cicdjacoco.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewCoach(@RequestBody  CoachDto request){
        return coachService.createNewCoach(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCoach(){
        return coachService.getAllCoach();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCoachById(@PathVariable Long id){
        return coachService.getCoachById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCoach(@PathVariable Long id,@RequestBody CoachDto request){
        return coachService.updateCoach(id,request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCoach(@PathVariable Long id){
        return coachService.deleteCoach(id);
    }
}
