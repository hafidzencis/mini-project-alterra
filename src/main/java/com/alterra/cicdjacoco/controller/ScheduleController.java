package com.alterra.cicdjacoco.controller;

import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
import com.alterra.cicdjacoco.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewSchedule(@RequestBody ScheduleDto request){
        return scheduleService.createNewSchedule(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllSchedule(){
        return scheduleService.getAllSchedule();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable Long id){
        return scheduleService.getScheduleById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateSchedule(@RequestBody ScheduleDto request,@PathVariable Long id){
        return scheduleService.updateSchedule(request,id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long id){
        return scheduleService.deleteSchedule(id);
    }
}
