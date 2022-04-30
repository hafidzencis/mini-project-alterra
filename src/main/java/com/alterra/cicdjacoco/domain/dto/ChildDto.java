package com.alterra.cicdjacoco.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ChildDto {
    private Long id;
    private String playerName;
    private String placeDob;
    private LocalDate dob;

}
