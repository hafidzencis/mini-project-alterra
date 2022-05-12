package com.alterra.cicdjacoco.domain.dto;

import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
    private Long id;
    private String teamName;
    private CoachDto coach;
    private ScheduleDto schedule;
    //    private Set<ChildDao> childs;
}
