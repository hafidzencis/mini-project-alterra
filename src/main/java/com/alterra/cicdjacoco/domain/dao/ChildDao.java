package com.alterra.cicdjacoco.domain.dao;


import com.alterra.cicdjacoco.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child")//thread
@SQLDelete(sql = "UPDATE child SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class ChildDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name",nullable = false)
    private String playerName;

    @Column(name = "place_dob",nullable = false)
    private String placeDob;

    @Column(name = "dob",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;

    @ManyToOne
    private UserDao user;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "childs")
    private List<ChooseTeamDao> chooseTeamDaos;

}
