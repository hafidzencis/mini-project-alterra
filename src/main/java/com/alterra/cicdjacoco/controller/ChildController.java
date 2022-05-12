package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/child")
public class ChildController {
    @Autowired
    private ChildService childService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllChild(){
        return childService.getAllChild();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getChildByid(@PathVariable Long id){
        return childService.getChildById(id);
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
    public ResponseEntity<Object> deleteChild(@PathVariable Long id){
        return childService.deleteChild(id);
    }




}
