package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
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
public class ChildService {
    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllChild() {

        try {
            log.info("Executing get all child");
            List<ChildDao> childDaoList = childRepository.findAll();
            List<ChildDto> childDtoList = new ArrayList<>();

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
            log.info("Executing get child by id");
            Optional<ChildDao> childDaoOptional = childRepository.findById(id);
            if (childDaoOptional.isEmpty()){
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            ChildDao childDao = childDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(childDao,ChildDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an errot getChildById, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createNewChild(ChildDto childDto){
        try {
            log.info("Executing create new child ");

            ChildDao childDao = ChildDao.builder()
                    .playerName(childDto.getPlayerName())
                    .placeDob(childDto.getPlaceDob())
                    .dob(childDto.getDob())
                    .build();
            childRepository.save(childDao);

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(childDao,ChildDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an erroy create new product, Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateChild(ChildDto childDto, Long id) {

        try {
            Optional<ChildDao> childDaoOptional = childRepository.findById(id);

            if (childDaoOptional.isEmpty()) {
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
                Optional<ChildDao> optionalChildDao = childRepository.findById(id);

                if (optionalChildDao.isEmpty()){
                    return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
                }

                childRepository.deleteById(id);
               return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
            }catch (Exception e){
                log.error("Get an error by delete child, Error: {}", e.getMessage());
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

}
