package com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller;

import com.gidon.gidon_aniz_couponsystem_spring_boot.service.LoginService;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    private  final Map<String, ClientSession> tokens;


    @PostMapping("/login")
    public ResponseEntity <List<String>> login(@RequestBody UserCredentials credentials) {

        String token = loginService.generateToken();
        ClientSession session = loginService.createSession(credentials.getEmail(), credentials.getPassword());

        tokens.put(token, session);

        List<String> stringList = List.of(token, session.getClientType());

        return ResponseEntity.ok(stringList);
    }
}
