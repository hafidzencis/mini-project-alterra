package com.alterra.cicdjacoco.domain.dto;

import com.alterra.cicdjacoco.domain.dao.TeamDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleDto {
    private Long id;
    private String daySchedule;
    private String timeSchedule;
}
