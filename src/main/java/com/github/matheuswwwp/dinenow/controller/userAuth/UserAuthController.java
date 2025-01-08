package com.github.matheuswwwp.dinenow.controller.userAuth;

import com.github.matheuswwwp.dinenow.DTO.user.UserSigninDTO;
import com.github.matheuswwwp.dinenow.DTO.user.UserSignupDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.CustomValidator;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.service.userAuth.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth")
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
    @Autowired
    private UserAuthService authService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenProvider jwtProvider;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new CustomValidator().getMessage(ex);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid UserSigninDTO data) {
        logger.info("Signin - init signin");
        var userModel = Mapper.parseObject(data, User.class);
        return authService.signin(userModel);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignupDTO data) {
        logger.info("Signup - init signup");
        var userModel = Mapper.parseObject(data, User.class);
        return authService.signup(userModel);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        logger.info("RefreshToken - init refreshToken");
        var claims = jwtProvider.getClaimFromToken(jwtProvider.resolveToken(request));
        if(claims == null) {
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return authService.refreshToken(refreshToken, claims.getUser_id());
    }
}
