package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
import com.alterra.cicdjacoco.repository.TeamRepository;
import com.alterra.cicdjacoco.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/team")

public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewTeam(@RequestBody TeamDto request){
        return teamService.createNewTeam(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllTeam(){
        return teamService.getAllTeam();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTeamById(@PathVariable Long id){
        return teamService.getTeambyId(id);
    }

//    @GetMapping(value = "/search")
//    public ResponseEntity<Object> findTeamByName(@RequestParam(value = "team_name",required = false)
//                                                                    String teamName){
//        return teamService.findTeamByName(teamName);
//    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateTeam(@PathVariable Long id,@RequestBody TeamDto request){
        return teamService.updateTeam(id,request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long id){
        return teamService.deleteTeam(id);
    }
}
