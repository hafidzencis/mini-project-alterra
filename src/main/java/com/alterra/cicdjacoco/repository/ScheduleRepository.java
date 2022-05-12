package com.alterra.cicdjacoco.repository;


import com.alterra.cicdjacoco.domain.dao.ScheduleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleDao,Long> {

}
