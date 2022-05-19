package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.service.ChildService;
import com.alterra.cicdjacoco.service.UserService;
import com.alterra.cicdjacoco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/child")
public class ChildController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllChild(Principal principal){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return childService.getAllChild();
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getChildByid(Principal principal,@PathVariable Long id){

        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return childService.getChildById(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewChild(@RequestBody ChildDto request){

        return childService.createNewChild(request);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateChild(@RequestBody ChildDto request,@PathVariable Long id){
        return childService.updateChild(request,id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteChild(Principal principal,@PathVariable Long id){
        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
        if (user.getAuthor().equals("ADMIN")){
            return childService.deleteChild(id);
        }
        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findChildByName(@RequestParam(value = "child_name",required = false) String child){
        return childService.findChildByName(child);
    }


}
