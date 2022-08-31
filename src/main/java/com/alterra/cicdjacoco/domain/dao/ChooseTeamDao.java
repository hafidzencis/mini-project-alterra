package com.alterra.cicdjacoco.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_choose_team")

public class ChooseTeamDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private ChildDao childs;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamDao teams;
}
