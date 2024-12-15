package com.logic.project.repositories;

import com.logic.project.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
}
