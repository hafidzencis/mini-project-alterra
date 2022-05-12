package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.service.ChooseTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/chooseteam")
public class ChooseTeamController {

    @Autowired
    private ChooseTeamService chooseTeamService;

    @PostMapping(value = "/child/{request_child}/team/{request_team}")
    ResponseEntity<Object> createNewChooseTeam(@PathVariable Long request_child,@PathVariable Long request_team){
        return chooseTeamService.createNewChooseTeam(request_child,request_team);

    }

    @GetMapping(value = "")
    ResponseEntity<Object> getAllChooseTeam(){
        return chooseTeamService.getAllChooseTeam();
    }
}
