package com.springboottask.SpringSecurityTechProject.repository;

import com.springboottask.SpringSecurityTechProject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT u FROM Users u WHERE u.username = :username ")
    Optional<Users> findUsersByUserName(@Param("username") String username);

}
