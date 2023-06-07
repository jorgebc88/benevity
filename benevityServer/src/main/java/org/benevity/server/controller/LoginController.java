package org.benevity.server.controller;

import org.benevity.server.config.security.JwtTokenUtil;
import org.benevity.server.config.security.MyUserDetails;
import org.benevity.server.openapi.api.LoginApi;
import org.benevity.server.openapi.model.AuthRequest;
import org.benevity.server.openapi.model.AuthResponse;
import org.benevity.server.repository.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    LoginController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                                                                                                                        authRequest.getPassword()));
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();

        if (authentication.isAuthenticated()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwtToken(jwtTokenUtil.generateAccessToken(user));
            return ResponseEntity.ok(authResponse);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
