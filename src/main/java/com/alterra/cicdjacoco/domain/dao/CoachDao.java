package com.alterra.cicdjacoco.domain.dao;

import com.alterra.cicdjacoco.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_coach")
@SQLDelete(sql = "UPDATE coach SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class CoachDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_name", nullable = false)
    private String coachName;

    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;

    @Column(name = "address", nullable = false)
    private String address;
}


