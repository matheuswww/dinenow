package com.github.matheuswwwp.dinenow.service.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String authenticate() {
        return "token";
    }
}
