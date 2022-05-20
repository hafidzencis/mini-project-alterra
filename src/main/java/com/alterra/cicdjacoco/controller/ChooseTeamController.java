package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.service.ChooseTeamService;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/chooseteam")
public class ChooseTeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChooseTeamService chooseTeamService;


    @PostMapping(value = "/child/{request_child}/team/{request_team}")
    ResponseEntity<Object> createNewChooseTeam(Principal principal, @PathVariable Long request_child,
                                               @PathVariable Long request_team){

      return chooseTeamService.createNewChooseTeam(request_child,request_team);
    }

    @GetMapping(value = "")
    ResponseEntity<Object> getAllChooseTeam(Principal principal){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return chooseTeamService.getAllChooseTeam();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findChildById(@RequestParam(value = "child_name",required = false) String childName){
        return chooseTeamService.findChildByName(childName);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteChooseTema(Principal principal, @PathVariable Long id){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return chooseTeamService.deleteChooseTeam(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);


    }
}
