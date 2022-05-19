package com.alterra.cicdjacoco.repository;

import com.alterra.cicdjacoco.domain.dao.ChildDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<ChildDao,Long> {
}
