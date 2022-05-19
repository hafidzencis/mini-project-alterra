package com.alterra.cicdjacoco.domain.dao;

import com.alterra.cicdjacoco.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="schedule")
@SQLDelete(sql = "UPDATE schedule SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class ScheduleDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_schedule",nullable = false)
    private String daySchedule;

    @Column(name = "time_schedule",nullable = false)
    private String timeSchedule;


}
