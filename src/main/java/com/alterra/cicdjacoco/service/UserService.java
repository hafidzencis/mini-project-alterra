package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.repository.UserRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = userRepository.getDistinctTopByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("Username Not Found");
        }
        return user;

    }

    public ResponseEntity<Object> getAllUser(){
        try {
            log.info("Executing get all user");
            List<UserDao> userDaoList = userRepository.findAll();
            List<UserDto> userDtoList = new ArrayList<>();

            if (userDaoList.isEmpty()){
                log.info("User List Not Found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (UserDao user:userDaoList) {
                userDtoList.add(mapper.map(user,UserDto.class));
            }
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,userDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error when executing get all user,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> getUserByid(Long id){
        try {
            log.info("Executing get user by id,id : {}",id);
            Optional<UserDao> userDaoOptional = userRepository.findById(id);

            if (userDaoOptional.isEmpty()){
                log.info("user not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            UserDao userDao = userDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(userDao,UserDto.class),HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing get user by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateService(Long id,UserDto request){
        try {
            log.info("Executing update team with id : {}",id);
            Optional<UserDao> userDaoOptional = userRepository.findById(id);
            if (userDaoOptional.isEmpty()){
                log.info("user not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            UserDao userDao = userDaoOptional.get();
            userDao.setNameUser(request.getNameUser());
            userDao.setTelephoneNumber(request.getTelephoneNumber());
            userDao.setAlamat(request.getAlamat());
            userRepository.save(userDao);

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(userDao,UserDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error by exeuting update team,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteUser(Long id){
        try {
            log.info("Executing delete user with id : {}",id);
            Optional<UserDao> userDaoOptional = userRepository.findById(id);
            if (userDaoOptional.isEmpty()){
                log.info("user not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            userRepository.delete(userDaoOptional.get());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error by executing delete user,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
