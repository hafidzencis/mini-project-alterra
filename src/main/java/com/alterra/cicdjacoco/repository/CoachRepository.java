package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.CoachDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachDao,Long> {
}
