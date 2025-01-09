package com.spellingbee.spellingbee.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RequestMapping("/auth")
@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(HttpServletRequest request) {
        String token = WebUtils.getCookie(request, "authToken").getValue();
        System.out.println(token + " has been received.");
        if (jwtService.validateToken(token)) {
            System.out.println(token + " is valid.");
            return ResponseEntity.ok().build();
        } else {
            System.out.println(token + " is NOT valid.");
            return ResponseEntity.badRequest().build();
        }
    }

}
