package com.github.matheuswwwp.dinenow.service.userAuth;

import com.auth0.jwt.exceptions.SignatureVerificationException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserAuthService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);
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
            var user = repository.findByUserEmail(email);
            if (user.isEmpty()) {
                throw new BadCredentialsException("invalid credentials");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            var tokenResponse = tokenProvider.createAccessToken(user.get().getEmail(), user.get().getId(), List.of());
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
            var savedData = repository.save(data);
            var tokenResponse = tokenProvider.createAccessToken(savedData.getEmail(), savedData.getId(), List.of());
            logger.info("Signup - success");
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        } catch (EmailAlreadyRegistredException e) {
            logger.error("Signup - error trying signup: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("email já cadastrado", HttpStatus.CONFLICT.value(), HttpMessages.conflict, null), HttpStatus.CONFLICT);
        } catch(Exception e) {
            logger.error("Signup - error trying signup: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> refreshToken(String refreshToken, String user_id) {
        try {
            var uuid_user_id = UUID.fromString(user_id);
            var tokenResponse = tokenProvider.refreshToken(refreshToken, uuid_user_id);
            logger.info("RefreshToken - success");
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        } catch(Exception e) {
            logger.error("RefreshToken - error trying refreshToken : {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
