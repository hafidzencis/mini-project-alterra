package com.alterra.cicdjacoco.domain.dao;


import com.alterra.cicdjacoco.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child")
public class ChildDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "place_dob")
    private String placeDob;

    @Column(name = "dob")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private UserDao user_id;

}
