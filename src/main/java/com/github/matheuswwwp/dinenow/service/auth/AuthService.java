package com.github.matheuswwwp.dinenow.service.auth;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.user.EmailAlreadyRegistredException;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signin(User data) {
        try {
            var email = data.getEmail();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            var tokenResponse = tokenProvider.createAccessToken(email, List.of());
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

    public ResponseEntity<?> signup(User data) {
        try {
            if(repository.findByUserEmail(data.getEmail()).isPresent()) {
                throw new EmailAlreadyRegistredException();
            }
            data.setPassword(passwordEncoder.encode(data.getPassword()));
            repository.save(data);
            var tokenResponse = tokenProvider.createAccessToken(data.getEmail(), List.of());
            logger.info("Signup - success");
            return ResponseEntity.ok(tokenResponse);
        } catch (EmailAlreadyRegistredException e) {
            logger.error("Signup - error trying signup: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("email já cadastrado", HttpStatus.CONFLICT.value(), HttpMessages.conflict, null), HttpStatus.CONFLICT);
        } catch(Exception e) {
            logger.error("Signup - error trying signup: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
