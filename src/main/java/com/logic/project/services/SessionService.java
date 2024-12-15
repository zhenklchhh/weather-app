package com.logic.project.services;

import com.logic.project.models.Session;
import com.logic.project.models.User;
import com.logic.project.repositories.SessionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class SessionService {

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Autowired
    private final SessionRepository sessionRepository;

    @Transactional
    public UUID createSessionForUser(User user) {
        Session session = new Session();
        session.setUserId(user.getId());
        session.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));
        session = saveSession(session);
        return session.getId();
    }

    public Session getSession(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Transactional
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(UUID id) {
        sessionRepository.deleteById(id);
    }
}
