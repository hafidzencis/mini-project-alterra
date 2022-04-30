package com.alterra.cicdjacoco.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coach")
public class CoachDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "telephone_number")
    private String telephone_number;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "coach_id")
    private List<TeamDao> teams;
}
