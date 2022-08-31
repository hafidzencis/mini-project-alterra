package com.alterra.cicdjacoco.domain.dao;

import com.alterra.cicdjacoco.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_team")
@SQLDelete(sql = "UPDATE team SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class TeamDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name",nullable = false)
    private String teamName;

    @ManyToOne
    private CoachDao coach;

    @OneToOne
    private ScheduleDao schedule;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "teams")
    private List<ChooseTeamDao> chooseTeamDaos;

}
