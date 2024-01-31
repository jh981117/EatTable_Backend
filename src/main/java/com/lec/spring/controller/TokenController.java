package com.lec.spring.controller;

import com.lec.spring.config.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
@CrossOrigin
public class TokenController {

    private final TokenProvider tokenProvider;

    public TokenController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        boolean isValid = tokenProvider.validateToken(token);

        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }
}