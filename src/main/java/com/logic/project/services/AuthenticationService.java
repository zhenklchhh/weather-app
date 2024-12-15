package com.logic.project.services;

import com.logic.project.models.Session;
import com.logic.project.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class AuthenticationService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    public User getAuthenticatedUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        UUID sessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    try {
                        sessionId = UUID.fromString(cookie.getValue());
                    } catch (IllegalArgumentException e) {
                        log.error("Invalid UUID in cookie: {}", cookie.getValue(), e);
                        return null;
                    }
                    break;
                }
            }
        }

        if (sessionId == null) {
            return null;
        }
        Session session = sessionService.getSession(sessionId);
        if (session == null) {
            return null;
        }

        Optional<User> user = userService.getUserBySession(session);
        return user.orElse(null);
    }

    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        UUID sessionId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    try {
                        sessionId = UUID.fromString(cookie.getValue());
                    } catch (IllegalArgumentException e) {
                        log.error("Invalid UUID in cookie: {}", cookie.getValue(), e);
                        return;
                    }
                    break;
                }
            }
        }

        if (sessionId != null) {
            sessionService.deleteSession(sessionId);
            Cookie cookie = new Cookie("sessionId", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

    }
}
