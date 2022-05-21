package com.alterra.cicdjacoco.service;


import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.CoachDao;
import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.alterra.cicdjacoco.domain.dto.ChildDto;
import com.alterra.cicdjacoco.domain.dto.TeamDto;
import com.alterra.cicdjacoco.repository.ChildRepository;
import com.alterra.cicdjacoco.repository.CoachRepository;
import com.alterra.cicdjacoco.repository.ScheduleRepository;
import com.alterra.cicdjacoco.repository.TeamRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Slf4j
@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> createNewTeam(TeamDto request){
        try {
            log.info("Executing crate new team");
            Optional<CoachDao> coachDaoOptional = coachRepository.findById(request.getCoach().getId());
            if (coachDaoOptional.isEmpty()){
                log.info("Coach not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<ScheduleDao> scheduleDaoOptional = scheduleRepository.findById(request.getSchedule().getId());
            if (scheduleDaoOptional.isEmpty()){
                log.info("Schedule not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            TeamDao teamDao = mapper.map(request,TeamDao.class);
            teamDao.setCoach(coachDaoOptional.get());
            teamDao.setSchedule(scheduleDaoOptional.get());
            teamRepository.save(teamDao);
            TeamDto teamDto = mapper.map(teamDao,TeamDto.class);

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,teamDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error executing create new team,Error : {} ",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllTeam(){
        try {
            log.info("Executing get all team ");
            List<TeamDao> teamDaoList = teamRepository.findAll();
            List<TeamDto> teamDtoList = new ArrayList<>();

            for (TeamDao team: teamDaoList) {
                teamDtoList.add(mapper.map(team,TeamDto.class));
            }

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,teamDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An error get all team, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getTeambyId(Long id){
        try {
            log.info("Executing getTeamById with id : {}",id);
            Optional<TeamDao> teamDaoOptional = teamRepository.findById(id);

            if (teamDaoOptional.isEmpty()){
                log.info("team not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            TeamDao teamDao = teamDaoOptional.get();
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(teamDao,TeamDto.class),HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error by executing get team by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Object> updateTeam(Long id, TeamDto request){
        try {
            log.info("Executing update Team");
            Optional<TeamDao> teamDaoOptional = teamRepository.findById(id);

            if (teamDaoOptional.isEmpty()){
                log.info("team not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            TeamDao teamDao = teamDaoOptional.get();
            teamDao.setTeamName(request.getTeamName());
            teamRepository.save(teamDao);

            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(teamDao,TeamDto.class),HttpStatus.OK);


        }catch (Exception e){
            log.error("Get an error by executing update team,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   public ResponseEntity<Object> findTeamByName(String teamName) {
       try {
           log.info("Executing find team by name, TeamName : {}",teamName);
           List<TeamDao> teamDaos = teamRepository.findTeamByName(teamName);
           List<TeamDto> teamDtoList = new ArrayList<>();

           if (teamDaos.isEmpty()){
               log.info("Team name not found");
               return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
           }

           for (TeamDao teamDao : teamDaos) {
               teamDtoList.add(mapper.map(teamDao, TeamDto.class));
           }
           return ResponseUtil.build(ResponseMassage.KEY_FOUND, teamDtoList, HttpStatus.OK);
       }catch (Exception e){
           log.error("Get an error by executing find team by name, Error : {}",e.getMessage());
           return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
       }


   }

    public ResponseEntity<Object> deleteTeam(Long id){
        try {
            Optional<TeamDao> teamDaoOptional = teamRepository.findById(id);

            if (teamDaoOptional.isEmpty()){
                log.info("team not found");
                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            teamRepository.delete(teamDaoOptional.get());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing delete Team");
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
