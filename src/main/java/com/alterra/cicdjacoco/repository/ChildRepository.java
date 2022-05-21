package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.ChildDao;
import com.alterra.cicdjacoco.domain.dao.TeamDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<ChildDao,Long> {

    @Query(value = "SELECT t FROM ChildDao t Where t.playerName LIKE %:child% ")
    List<ChildDao> findChildByName(@Param("child") String child);

}
