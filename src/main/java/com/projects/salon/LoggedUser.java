package com.projects.salon;

import com.projects.salon.entity.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class LoggedUser extends org.springframework.security.core.userdetails.User {
    private final Employee loggedUser;

    public LoggedUser(Employee user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        loggedUser = user;
    }

    private static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object user = auth.getPrincipal();
        return (user instanceof LoggedUser) ? (LoggedUser) user : null;
    }

    private static LoggedUser get() {
        LoggedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int getId() {
        return get().asEmployee().getId();
    }

    public Employee asEmployee() {
        return loggedUser;
    }


}