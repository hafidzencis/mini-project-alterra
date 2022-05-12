package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.ChooseTeamDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChooseTeamRepository extends JpaRepository<ChooseTeamDao,Long> {
}
