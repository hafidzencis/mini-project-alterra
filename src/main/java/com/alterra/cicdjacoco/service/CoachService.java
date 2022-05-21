package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dto.CoachDto;
import com.alterra.cicdjacoco.repository.CoachRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CoachService {
    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getCoachById(Long id){
        try {
            log.info("Executing find coach by id,Id : {}",id);
            Optional<CoachDao> coachDaoOptional = coachRepository.findById(id);
            if (coachDaoOptional.isEmpty()){
                log.error("coach not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            CoachDao coachDao = coachDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(coachDao,CoachDto.class),HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error while executing coach by id,Error : {} ",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createNewCoach(CoachDto request){
        try {
            log.info("Executing create new coach");
//            CoachDao coachDao = mapper.map(request,CoachDao.class);
            CoachDao coachDao = CoachDao.builder()
                    .coachName(request.getCoachName())
                    .telephoneNumber(request.getTelephoneNumber())
                    .address(request.getAddress())
                    .build();
            coachRepository.save(coachDao);
            CoachDto coachDto = mapper.map(coachDao,CoachDto.class);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,coachDto, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An  error by executing create New Caoch, Error: {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> getAllCoach(){
        try {
            log.info("Executing gea all child");
            List<CoachDao> coachDaoList = coachRepository.findAll();
            List<CoachDto> coachDtoList = new ArrayList<>();

            for (CoachDao coach : coachDaoList ) {
             coachDtoList.add(mapper.map(coach, CoachDto.class));
            }
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,coachDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error for executing get All coach, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> updateCoach(Long id, CoachDto request){
        try {
            log.info("Executing update coach with id, Id : {}",id);
            Optional<CoachDao> coachDaoOptional = coachRepository.findById(id);
            if (coachDaoOptional.isEmpty()){
                log.error("coach not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            CoachDao coachDao = coachDaoOptional.get();
            coachDao.setCoachName(request.getCoachName());
            coachDao.setTelephoneNumber(request.getTelephoneNumber());
            coachDao.setAddress(request.getAddress());

            coachRepository.save(coachDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(coachDao,CoachDto.class),HttpStatus.OK);
        } catch (Exception e){
            log.error("Get An error by update coach , Error: {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> deleteCoach(Long id){
        try {
            log.info("Executing delete coach by id, Id : {}",id);
            Optional<CoachDao> coachDaoOptional = coachRepository.findById(id);

            if (coachDaoOptional.isEmpty()){
                log.error("coach not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            coachRepository.delete(coachDaoOptional.get());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Error executing delete Coach, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}