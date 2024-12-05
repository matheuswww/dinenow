package com.github.matheuswwwp.dinenow.controller.order;

import com.github.matheuswwwp.dinenow.DTO.order.CreateOrderDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.CustomValidator;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.service.order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtTokenProvider jwtProvider;
    @Autowired
    private HttpServletRequest request;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new CustomValidator().getMessage(ex);
    }

    @PostMapping(value = "/createOrder")
    public ResponseEntity<?> CreateOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO) {
        logger.info("CreateOrder - init CreateOrder");
        var claims = jwtProvider.getClaimFromToken(jwtProvider.resolveToken(request));
        if(claims == null) {
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        var order = Mapper.parseObject(createOrderDTO, Order.class);
        return orderService.CreateOrder(createOrderDTO, claims.getUser_id());
    }

  
}
