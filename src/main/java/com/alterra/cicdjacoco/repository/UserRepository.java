package com.alterra.cicdjacoco.repository;



import com.alterra.cicdjacoco.domain.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDao,Long> {

}
