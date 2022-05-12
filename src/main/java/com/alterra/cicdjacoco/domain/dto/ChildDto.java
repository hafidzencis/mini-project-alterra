package com.alterra.cicdjacoco.domain.dto;


import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildDto {
    private Long id;
    private String playerName;
    private String placeDob;
    private LocalDate dob;
    private UserDto user;
//    private Set<TeamDao> teams;

//    public void teams(TeamDto teamDao){
//        teams.add(teamDao);
//    }


}