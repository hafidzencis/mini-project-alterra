package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
import com.alterra.cicdjacoco.repository.ScheduleRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository  scheduleRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getScheduleById(Long id){
        try{
            log.info("Executing get schedule by id,Id : {}",id);
            Optional<ScheduleDao> scheduleDaoOptional = scheduleRepository.findById(id);

            if (scheduleDaoOptional.isEmpty()){
                log.info("schedule not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            ScheduleDao scheduleDao = scheduleDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(scheduleDao,ScheduleDto.class),HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing get schedule by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createNewSchedule(ScheduleDto request){
        try{
            log.info("Executing create New Schedule");
            ScheduleDao scheduleDao = ScheduleDao.builder()
                    .daySchedule(request.getDaySchedule())
                    .timeSchedule(request.getTimeSchedule())
                    .build();
            scheduleRepository.save(scheduleDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(scheduleDao,ScheduleDto.class), HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An error by executing create New Schedule, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> getAllSchedule(){
        try {
            log.info("Executing by get all schedule");
            List<ScheduleDao> scheduleDaoList = scheduleRepository.findAll();
            List<ScheduleDto> scheduleDtoList = new ArrayList<>();

            for(ScheduleDao schedule : scheduleDaoList){
                scheduleDtoList.add(mapper.map(schedule,ScheduleDto.class));
            }

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,scheduleDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by get all schedule, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateSchedule(ScheduleDto request, Long id) {

        try {
            Optional<ScheduleDao> scheduleDaoOptional = scheduleRepository.findById(id);

            if (scheduleDaoOptional.isEmpty()) {
                log.info("schedule not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ScheduleDao scheduleDao = scheduleDaoOptional.get();
            scheduleDao.setDaySchedule(request.getDaySchedule());
            scheduleDao.setTimeSchedule(request.getTimeSchedule());
            scheduleRepository.save(scheduleDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, mapper.map(scheduleDao,ScheduleDto.class), HttpStatus.OK);


        } catch (Exception e) {
            log.error("Get an error by update child, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> deleteSchedule(Long id){
        try {
            log.info("Executing delete by id, Id : {}",id);
            Optional<ScheduleDao> scheduleDaoOptional = scheduleRepository.findById(id);

            if (scheduleDaoOptional.isEmpty()){
                log.info("schedule not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            scheduleRepository.delete(scheduleDaoOptional.get());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An error delete schedule by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}
