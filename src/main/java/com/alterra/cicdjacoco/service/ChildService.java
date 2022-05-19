package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
import com.alterra.cicdjacoco.repository.UserRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChildService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ModelMapper mapper;

//    @Autowired
//    private TeamRepository teamRepository;

    public ResponseEntity<Object> getAllChild() {

        try {
            log.info("Executing get all child");
            List<ChildDao> childDaoList = childRepository.findAll();
            List<ChildDto> childDtoList = new ArrayList<>();

            if (childDaoList.isEmpty()){
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            for (ChildDao child : childDaoList) {
                childDtoList.add(mapper.map(child,ChildDto.class));
            }
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, childDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when get all child, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> getChildById(Long id){
        try {
            log.info("Executing get child by id,id : {}",id);
            Optional<ChildDao> childDaoOptional = childRepository.findById(id);
            if (childDaoOptional.isEmpty()){
                log.info("child not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            ChildDao childDao = childDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(childDao,ChildDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error getChildById, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> createNewChild(ChildDto childDto){

        try {
            log.info("Executing create new child ");
            Optional<UserDao> userDaoOptional = userRepository.findById(childDto.getUser().getId());

            if (userDaoOptional.isEmpty()){
                log.info("child not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }


            ChildDao childDao = ChildDao.builder()
                    .playerName(childDto.getPlayerName())
                    .placeDob(childDto.getPlaceDob())
                    .dob(childDto.getDob())
                    .build();
            childDao.setUser(userDaoOptional.get());
            childRepository.save(childDao);

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(childDao,ChildDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error create new child, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        public ResponseEntity<Object> updateChild(ChildDto childDto, Long id) {

        try {
            log.info("Executing update child with id,Id : {}",id);
            Optional<ChildDao> childDaoOptional = childRepository.findById(id);

            if (childDaoOptional.isEmpty()) {
                log.info("child not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ChildDao childDao = childDaoOptional.get();
            childDao.setPlayerName(childDto.getPlayerName());
            childDao.setPlaceDob(childDto.getPlaceDob());
            childDto.setDob(childDto.getDob());

            childRepository.save(childDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, mapper.map(childDao,ChildDto.class), HttpStatus.OK);


        } catch (Exception e) {
            log.error("Get an error by update child, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



        public ResponseEntity<Object> deleteChild(Long id){
            try {
                log.info("Executing delete child with id : {}",id);
                Optional<ChildDao> optionalChildDao = childRepository.findById(id);

                if (optionalChildDao.isEmpty()){
                    log.info("child not found");
                    return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
                }

                childRepository.delete(optionalChildDao.get());
               return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
            }catch (Exception e){
                log.error("Get an error by delete child, Error: {}", e.getMessage());
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    public ResponseEntity<Object> findChildByName(String child) {
        try {
            log.info("Executing find team by name, TeamName : {}", child);
            List<ChildDao> childDaoList = childRepository.findChildByName(child);
            List<ChildDto> childDtoList = new ArrayList<>();

            if (childDaoList.isEmpty()) {
                log.info("Child name not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            for (ChildDao childs : childDaoList) {
                childDtoList.add(mapper.map(childs, ChildDto.class));
            }
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, childDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing find child by name, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
