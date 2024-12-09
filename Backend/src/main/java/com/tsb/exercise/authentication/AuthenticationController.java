package com.tsb.exercise.authentication;


import com.tsb.exercise.security.utils.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthenticationController {

    private final JwtUtil jwtUtil;

    public AuthenticationController(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationDetails authenticationDetails) {
        String token = jwtUtil.generateToken("101");
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
