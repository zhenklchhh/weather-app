package com.logic.project.services;

import com.logic.project.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

}
