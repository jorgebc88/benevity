package org.benevity.server.controller;

import org.benevity.server.config.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Controller {

    protected static String getUserLoggedId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userLogged = (MyUserDetails) authentication.getPrincipal();
        return userLogged.getUser().getId();
    }
}
