package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.ChooseTeamDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChooseTeamRepository extends JpaRepository<ChooseTeamDao,Long> {


    @Query(value = "SELECT t FROM ChooseTeamDao t Where t.childName LIKE %:childName% ")
    List<ChooseTeamDao> findChildByName(@Param("childName") String childName);

}
