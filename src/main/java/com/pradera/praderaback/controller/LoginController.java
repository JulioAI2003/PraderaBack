package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.JwtRequest;
import com.pradera.praderaback.dto.JwtResponse;
import com.pradera.praderaback.security.CustomUserDetailsService;
import com.pradera.praderaback.security.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil util;

    @Autowired
    private CustomUserDetailsService service;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        try {
            JwtResponse jwtResponse;
            if (authenticate(request.getUsername(), request.getPassword())) {
                UserDetails user = service.loadUserByUsername(request.getUsername());
                jwtResponse = new JwtResponse(true, util.generateToken(user.getUsername()));
            } else {
                jwtResponse = new JwtResponse(false, null);
            }
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    private Boolean authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
