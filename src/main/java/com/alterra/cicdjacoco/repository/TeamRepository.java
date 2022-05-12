package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.TeamDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamDao,Long> {

    @Query(value = "SELECT t.* FROM team Where LIKE %teamName%")
    public List<TeamDao> findTeamByNamee(@Param("teamName") String teamName);


}
