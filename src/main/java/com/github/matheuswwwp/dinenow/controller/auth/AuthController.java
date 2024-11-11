package com.github.matheuswwwp.dinenow.controller.auth;

import com.github.matheuswwwp.dinenow.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public String authenticate() {
        return authService.authenticate();
    }
}
