package com.alterra.cicdjacoco.domain.dao;


import com.alterra.cicdjacoco.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="schedule")
public class ScheduleDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_schedule")
    private String daySchedule;

    @Column(name = "time_schedule")
    private String timeSchedule;


}
