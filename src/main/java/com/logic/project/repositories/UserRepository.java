package com.logic.project.repositories;

import com.logic.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
    Optional<User> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
