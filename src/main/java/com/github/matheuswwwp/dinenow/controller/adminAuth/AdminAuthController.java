package com.github.matheuswwwp.dinenow.controller.adminAuth;

import com.github.matheuswwwp.dinenow.DTO.admin.AdminSigninDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.CustomValidator;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.admin.Admin;
import com.github.matheuswwwp.dinenow.service.adminAuth.AdminAuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
    @Autowired
    private AdminAuthService authService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new CustomValidator().getMessage(ex);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid AdminSigninDTO data) {
        logger.info("Signin - init signin");
        var adminModel = Mapper.parseObject(data, Admin.class);
        return authService.signin(adminModel);
    }
}
