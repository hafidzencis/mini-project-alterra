package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
import com.alterra.cicdjacoco.repository.TeamRepository;
import com.alterra.cicdjacoco.service.TeamService;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/team")

public class TeamController {
    @Autowired
    private UserService userService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewTeam(Principal principal,@RequestBody TeamDto request){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return teamService.createNewTeam(request);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllTeam(Principal principal){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return teamService.getAllTeam();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTeamById(Principal principal,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return teamService.getTeambyId(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findTeamByName(@RequestParam(value = "team_name",required = false) String teamName){
        return teamService.findTeamByName(teamName);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateTeam(Principal principal,@PathVariable Long id,@RequestBody TeamDto request){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return teamService.updateTeam(id,request);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTeam(Principal principal, @PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return teamService.deleteTeam(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }
}
