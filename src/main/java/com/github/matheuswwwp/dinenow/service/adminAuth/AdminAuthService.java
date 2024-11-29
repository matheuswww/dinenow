package com.github.matheuswwwp.dinenow.service.adminAuth;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.model.admin.Admin;
import com.github.matheuswwwp.dinenow.repository.admin.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAuthService {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AdminRepository repository;

    public ResponseEntity<?> signin(Admin data) {
        try {
            var email = data.getEmail();
            var password = data.getPassword();
            var admin = repository.findByAdminEmail(email);
            if (admin.isEmpty()) {
                throw new BadCredentialsException("invalid credentials");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            var tokenResponse = tokenProvider.createAccessToken(admin.get().getEmail(), admin.get().getId(), List.of("ADMIN"));
            logger.info("Signin - success");
            return ResponseEntity.ok(tokenResponse);
        } catch (BadCredentialsException e) {
            logger.error("Signin - error trying singin: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("credenciais inválidas", HttpStatus.FORBIDDEN.value(), HttpMessages.forbidden, null), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Signin - error trying singin: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
