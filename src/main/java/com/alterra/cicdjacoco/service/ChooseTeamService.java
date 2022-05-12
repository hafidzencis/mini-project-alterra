package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.ChooseTeamDao;
import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.alterra.cicdjacoco.domain.dto.ChooseTeamDto;
import com.alterra.cicdjacoco.domain.dto.ScheduleDto;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
import com.alterra.cicdjacoco.repository.ChooseTeamRepository;
import com.alterra.cicdjacoco.repository.TeamRepository;
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
public class ChooseTeamService {

    @Autowired
    private ChooseTeamRepository chooseTeamRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<Object> createNewChooseTeam(Long request_child,Long request_team){
        try {
            log.info("Executing createn choose team");
            Optional<ChildDao> childDaoOptional = childRepository.findById(request_child);
            if (childDaoOptional.isEmpty()){
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<TeamDao> teamDaoOptional = teamRepository.findById(request_team);
            if (teamDaoOptional.isEmpty()){
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            ChooseTeamDao chooseTeamDao = ChooseTeamDao.builder()
                    .teams(teamDaoOptional.get())
                    .childs(childDaoOptional.get())
                    .build();
            chooseTeamRepository.save(chooseTeamDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,
                    mapper.map(chooseTeamDao, ChooseTeamDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get An error executing new choose team, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<Object> getAllChooseTeam(){
        try {
            log.info("Executing by get all choose team");
            List<ChooseTeamDao> chooseTeamDaoList = chooseTeamRepository.findAll();
            List<ChooseTeamDto> chooseTeamDtoList = new ArrayList<>();

            for(ChooseTeamDao choosteam : chooseTeamDaoList){
                chooseTeamDtoList.add(mapper.map(choosteam,ChooseTeamDto.class));
            }

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,chooseTeamDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error for executing choose team, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
