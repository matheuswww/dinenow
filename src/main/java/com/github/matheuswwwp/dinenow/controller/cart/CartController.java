package com.github.matheuswwwp.dinenow.controller.cart;

import com.github.matheuswwwp.dinenow.DTO.cart.CreateCartDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.CustomValidator;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.service.cart.CartService;
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
@RequestMapping("/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenProvider jwtProvider;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new CustomValidator().getMessage(ex);
    }

    @PostMapping(value = "/createCart")
    public ResponseEntity<?> CreateCart(@RequestBody @Valid CreateCartDTO data) {
        logger.info("CreateCart - init CreateCart");
        var claims = jwtProvider.getClaimFromToken(jwtProvider.resolveToken(request));
        if(claims == null) {
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return cartService.CreateCart(data.getQuantity(), claims.getUser_id(), data.getDishId());
    }
}
